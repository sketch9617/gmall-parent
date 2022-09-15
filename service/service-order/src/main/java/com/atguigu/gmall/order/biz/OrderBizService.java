package com.atguigu.gmall.order.biz;

import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;

/**
 * @author sketch
 * @date 2022/9/15 11:45
 * @description 订单业务biz=business
 */
public interface OrderBizService {

    /**
     * 获取订单确认需要的数据
     */
    OrderConfirmDataVo getConfirmData();

    /**
     * 生成交易流水号
     * 1、追踪整个订单
     * 2、作为防重令牌
     */
    String generateTradeNo();

    /**
     * 提交订单
     */
    Long submitOrder(OrderSubmitVo submitVo, String tradeNo);

    /**
     * 校验令牌
     */
    boolean checkTradeNo(String tradeNo);
}
