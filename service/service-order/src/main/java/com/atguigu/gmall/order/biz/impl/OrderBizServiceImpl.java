package com.atguigu.gmall.order.biz.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.cart.CartFeignClient;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.feign.user.UserFeignClient;
import com.atguigu.gmall.feign.ware.WareFeignClient;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.user.UserAddress;
import com.atguigu.gmall.model.vo.order.CartInfoVo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import com.atguigu.gmall.order.service.OrderInfoService;
import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderBizServiceImpl implements OrderBizService {

    @Autowired
    CartFeignClient cartFeignClient;
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    SkuProductFeignClient skuProductFeignClient;
    @Autowired
    WareFeignClient wareFeignClient;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public OrderConfirmDataVo getConfirmData() {
        OrderConfirmDataVo vo = new OrderConfirmDataVo();
        //1、获取购物车中选中的商品
        //是购物车服务返回的
        //购物车中的商品只代表在redis中存储的数据，并不能代表最新价格
        List<CartInfo> data = cartFeignClient.getChecked().getData();

        List<CartInfoVo> infoVos = data.stream()
                .map(cartInfo -> {
                    CartInfoVo infoVo = new CartInfoVo();
                    infoVo.setSkuId(cartInfo.getSkuId());
                    infoVo.setImgUrl(cartInfo.getImgUrl());
                    infoVo.setSkuName(cartInfo.getSkuName());
                    //只要用到价格，就需要再查一遍；只要单下了，以后价格就用订单说明的定死的价格
                    //实时查价
                    Result<BigDecimal> price = skuProductFeignClient.getSku1010Price(cartInfo.getSkuId());
                    infoVo.setOrderPrice(price.getData());
                    infoVo.setSkuNum(cartInfo.getSkuNum());

                    //查询商品库存  http://localhost:9001/hasStock?skuId=43&num=9997
                    //feign 声明式 HTTP 客户端
                    String stock = wareFeignClient.hasStock(cartInfo.getSkuId(), cartInfo.getSkuNum());
                    infoVo.setHasStock(stock);
                    return infoVo;
                }).collect(Collectors.toList());

        vo.setDetailArrayList(infoVos);

        //2、统计商品的总数量
        Integer totalNum = infoVos.stream().map(CartInfoVo::getSkuNum)
                .reduce((o1, o2) -> o1 + o2)
                .get();
        vo.setTotalNum(totalNum);

        //3、统计商品的总金额
        BigDecimal totalAmount = infoVos.stream()
                .map(item -> item.getOrderPrice().multiply(new BigDecimal(item.getSkuNum() + "")))
                .reduce((o1, o2) -> o1.add(o2)).get();
        vo.setTotalAmount(totalAmount);

        //4、获取用户收货地址列表
        Result<List<UserAddress>> addressList = userFeignClient.getUserAddressList();
        vo.setUserAddressList(addressList.getData());

        //5、生成一个追踪号
        //5.1、订单的唯一追踪号，对外交易号（和第三方交互）。
        //5.2、用来防重复提交。 做防重令牌
        String tradeNo = generateTradeNo();
        //令牌前端交一份
        vo.setTradeNo(tradeNo);
        return vo;
    }

    @Override
    public String generateTradeNo() {
        long millis = System.currentTimeMillis();
        UserAuthInfo info = AuthUtils.getCurrentAuthInfo();
        String tradeNo = millis + "_" + info.getUserId();
        //令牌redis存一份。
        redisTemplate.opsForValue()
                .set(SysRedisConst.ORDER_TEMP_TOKEN + tradeNo,"1",15, TimeUnit.MINUTES);
        return tradeNo;
    }
}
