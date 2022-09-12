package com.atguigu.gmall.gateway.filter;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.gateway.properties.AuthUrlProperties;
import com.atguigu.gmall.model.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author sketch
 * @date 2022/9/8 18:33
 * @description
 */
@Slf4j
@Component
public class GlobalAuthFilter implements GlobalFilter {

    AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    AuthUrlProperties urlProperties;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1.获取请求路径
        String path = exchange.getRequest().getURI().getPath();
        String uri = exchange.getRequest().getURI().toString();
        log.info("{} 请求开始", path);

        //2.无需登录的资源->直接放行
        for (String url : urlProperties.getNoAuthUrl()) {
            boolean match = matcher.match(url, path);
            if (match) {
                return chain.filter(exchange);
            }
        }

        //3.浏览器/api/inner请求->全部拒绝
        for (String url : urlProperties.getDenyUrl()) {
            boolean match = matcher.match(url, path);
            if (match) {
                //直接响应json数据
                Result<String> result = Result.build("", ResultCodeEnum.PERMISSION);
                return responseResult(result, exchange);
            }
        }

        //4.需要登录的请求->权限验证
        for (String url : urlProperties.getLoginAuthUrl()) {
            boolean match = matcher.match(url, path);
            if (match) {
                //获取token
                String tokenValue = getTokenValue(exchange);
                //校验token
                UserInfo info = getTokenUserInfo(tokenValue);
                //判断用户信息是否正确
                if (info != null) {
                    //redis中存在此用户
                    ServerWebExchange webExchange = userIdOrTempIdTransport(info, exchange);
                    return chain.filter(webExchange);
                } else {
                    //redis无此用户
                    return redirectToCustomPage(urlProperties.getLoginPage() + "?originUrl=" + uri, exchange);
                }
            }
        }

        //5.假请求,重定向登录
        String tokenValue = getTokenValue(exchange);
        UserInfo info = getTokenUserInfo(tokenValue);
        if (!StringUtils.isEmpty(tokenValue) && info == null) {
            return redirectToCustomPage(urlProperties.getLoginPage() + "?originUrl=" + uri, exchange);
        }

        //6.普通请求->透传用户id
        exchange = userIdOrTempIdTransport(info, exchange);
        return chain.filter(exchange);
    }

    /**
     * 5.用户id透传
     */
    private ServerWebExchange userIdOrTempIdTransport(UserInfo info, ServerWebExchange exchange) {
        //请求一旦发来，所有的请求数据是固定的，不能进行任何修改，只能读取
        ServerHttpRequest.Builder newReqbuilder = exchange.getRequest().mutate();

        //用户登录了
        if(info != null){
            newReqbuilder.header(SysRedisConst.USERID_HEADER,info.getId().toString());
        }
        //用户没登录
        String userTempId = getUserTempId(exchange);
        newReqbuilder.header(SysRedisConst.USERTEMPID_HEADER,userTempId);

        //放行的时候传改掉的exchange
        ServerWebExchange webExchange = exchange
                .mutate()
                .request(newReqbuilder.build())
                .response(exchange.getResponse())
                .build();
        return webExchange;
    }

    /**
     * 6.获取临时id
     */
    private String getUserTempId(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        //1、尝试获取头中的临时id
        String tempId = request.getHeaders().getFirst("userTempId");
        //2、如果头中没有，尝试获取cookie中的值
        if(StringUtils.isEmpty(tempId)){
            HttpCookie httpCookie = request.getCookies().getFirst("userTempId");
            if(httpCookie!=null){
                tempId = httpCookie.getValue();
            }
        }
        return tempId;
    }

    /**
     * 4.重定向到指定路径
     */
    private Mono<Void> redirectToCustomPage(String location, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        //1、重定向【302状态码 + 响应头中 Location: 新位置】
        response.setStatusCode(HttpStatus.FOUND);
        // http://passport.gmall.com/login.html?originUrl=http://gmall.com/
        response.getHeaders().add(HttpHeaders.LOCATION,location);

        //2、清除旧的错误的Cookie[token]（同名cookie并max-age=0）解决无限重定向问题
        ResponseCookie tokenCookie = ResponseCookie
                .from("token", "777")
                .maxAge(0)
                .path("/")
                .domain(".gmall.com")
                .build();
        response.getCookies().set("token",tokenCookie);

        //3、响应结束
        return response.setComplete();
    }

    /**
     * 3.根据token的值去redis中查到用户信息
     */
    private UserInfo getTokenUserInfo(String tokenValue) {
        String json = redisTemplate.opsForValue().get(SysRedisConst.LOGIN_USER + tokenValue);
        if(!StringUtils.isEmpty(json)){
            return Jsons.toObj(json,UserInfo.class);
        }
        return null;
    }

    /**
     * 2.从cookie或请求头中取到token对应的值
     */
    private String getTokenValue(ServerWebExchange exchange) {
        //1、先检查Cookie中有没有这个 token
        String tokenValue = "";
        HttpCookie token = exchange.getRequest()
                .getCookies()
                .getFirst("token");
        if(token != null){
            tokenValue = token.getValue();
            return tokenValue;
        }

        //2、说明cookie中没有,去header找
        tokenValue = exchange.getRequest()
                .getHeaders()
                .getFirst("token");

        return tokenValue;
    }

    /**
     * 1.响应一个结果
     */
    private Mono<Void> responseResult(Result<String> result, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        String jsonStr = Jsons.toStr(result);

        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(dataBuffer));
    }
}