package com.atguigu.gmall.order.service;

import com.atguigu.gmall.model.enums.ProcessStatus;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author TANGLED
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service
* @createDate 2022-09-15 10:00:13
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 根据页面提交的数据生成一个数据库的订单
     */
    Long saveOrder(OrderSubmitVo submitVo,String tradeNo);

    /**
     * 幂等修改订单状态
     */
    void changeOrderStatus(Long orderId, Long userId,
                           ProcessStatus closed,
                           List<ProcessStatus> expected);
}
