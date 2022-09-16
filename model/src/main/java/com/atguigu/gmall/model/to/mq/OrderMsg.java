package com.atguigu.gmall.model.to.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sketch
 * @date 2022/9/16 19:45
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderMsg {
    private Long orderId;
    private Long userId;
}
