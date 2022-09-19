package com.atguigu.gmall.pay.controller;

import com.alipay.api.AlipayApiException;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.pay.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author sketch
 * @date 2022/9/19 19:11
 * @description
 */


@Slf4j
@RequestMapping("/api/payment")
@Controller
public class PayController {
    @Autowired
    AliPayService alipayService;

    /**
     * 1.跳转支付宝二维码收银台
     */
    @ResponseBody
    @GetMapping("/alipay/submit/{orderId}")
    public String alipayPage(@PathVariable("orderId") Long orderId) throws AlipayApiException {
        String content = alipayService.getAlipayPageHtml(orderId);
        return content;
    }

    /**
     * 2.支付成功:跳转到支付成功页
     */
    @GetMapping("/paysuccess") //同步通知地址
    public String paysuccess(@RequestParam Map<String,String> paramMaps) throws AlipayApiException {
        System.out.println("支付成功同步通知页：收到的参数："+paramMaps);
        //1、如果要在这里改订单状态，先验签。验证是否支付宝给我们发来的数据
        boolean b =  alipayService.rsaCheckV1(paramMaps);
        if(b){
            //验签通过
            System.out.println("正在修改订单状态....订单信息："+paramMaps);
        }
        return "redirect:http://gmall.com/pay/success.html";
    }

    /**
     * 3.支付成功:异步通知
     */
    @ResponseBody
    @RequestMapping("/success/notify")
    public String notifySuccess(@RequestParam Map<String,String> param) throws AlipayApiException {

        boolean b = alipayService.rsaCheckV1(param);
        if(b){
            log.info("异步通知抵达。支付成功，验签通过。数据：{}", Jsons.toStr(param));
        }else {
            return "error";
        }
        return "success";
    }
}
