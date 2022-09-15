package com.atguigu.gmall.order;

import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.order.mapper.OrderInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
public class OrderTest {

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Test
    void queryOne(){
        OrderInfo orderInfo = orderInfoMapper.selectById(197L);
        System.out.println(orderInfo);
    }

    @Test
    public void ShardingInsert(){
        OrderInfo info = new OrderInfo();
        info.setTotalAmount(new BigDecimal("777"));
        info.setUserId(1L);
        orderInfoMapper.insert(info);
        System.out.println("1号用户订单插入完成....去 1库1表找");

        OrderInfo info2 = new OrderInfo();
        info2.setTotalAmount(new BigDecimal("666"));
        info2.setUserId(2L);
        orderInfoMapper.insert(info2);
        System.out.println("2号用户订单插入完成....去 0库2表找");
    }
}
