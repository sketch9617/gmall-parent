package com.atguigu.gmall.order.api;

import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import com.atguigu.gmall.order.service.OrderInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sketch
 * @date 2022/9/15 11:44
 * @description
 */
@RequestMapping("/api/inner/rpc/order")
@RestController
public class OrderApiController {


    @Autowired
    OrderBizService orderBizService;

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 获取订单确认页需要的数据
     */
    @GetMapping("/confirm/data")
    public Result<OrderConfirmDataVo> getOrderConfirmData(){
        OrderConfirmDataVo vo =  orderBizService.getConfirmData();
        return Result.ok(vo);
    }

    /**
     * 下单后获取某个订单数据
     */
    @GetMapping("/info/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId){

        Long userId = AuthUtils.getCurrentAuthInfo().getUserId();

        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getId, orderId)
                .eq(OrderInfo::getUserId, userId);
        OrderInfo one = orderInfoService.getOne(wrapper);
        return Result.ok(one);
    }
}
