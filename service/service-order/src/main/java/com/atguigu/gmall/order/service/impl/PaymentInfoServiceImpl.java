package com.atguigu.gmall.order.service.impl;

import com.atguigu.gmall.model.payment.PaymentInfo;
import com.atguigu.gmall.order.service.PaymentInfoService;
import com.atguigu.gmall.order.mapper.PaymentInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author TANGLED
* @description 针对表【payment_info(支付信息表)】的数据库操作Service实现
* @createDate 2022-09-15 10:00:13
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService{

}




