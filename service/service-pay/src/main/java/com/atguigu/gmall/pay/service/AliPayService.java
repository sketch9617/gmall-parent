package com.atguigu.gmall.pay.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

/**
 * @author sketch
 * @date 2022/9/19 19:14
 * @description
 */
public interface AliPayService {
    /**
     * 1.生成指定订单的支付页
     * @param orderId
     * @return
     */
    String getAlipayPageHtml(Long orderId) throws AlipayApiException;

    /**
     * 支付宝验签
     * @param paramMaps
     * @return
     */
    boolean rsaCheckV1(Map<String, String> paramMaps) throws AlipayApiException;
}
