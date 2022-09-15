package com.atguigu.gmall.model.vo.order;

import lombok.Data;
import java.util.List;

/**
 * @author sketch
 * @date 2022/9/15 21:14
 * @description 订单提交 数据模型
 */
@Data
public class OrderSubmitVo {
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String paymentWay;
    private String orderComment;
    private List<CartInfoVo> orderDetailList;
}
