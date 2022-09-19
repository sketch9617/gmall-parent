package com.atguigu.gmall.feign.ware.callback;

import com.atguigu.gmall.feign.ware.WareFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author sketch
 * @date 2022/9/17 9:05
 * @description
 */
@Component
public class WareFeignClientCallBack implements WareFeignClient {
    /**
     * 错误兜底
     */
    @Override
    public String hasStock(Long skuId, Integer num) {
        //统一显示有货
        return "1";
    }
}