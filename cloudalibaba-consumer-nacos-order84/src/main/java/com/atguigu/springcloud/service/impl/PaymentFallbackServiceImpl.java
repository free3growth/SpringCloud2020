package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFallbackService;
import org.springframework.stereotype.Component;

/**
 * @author: bin.wang
 * @date: 2020/7/23 15:36
 */
@Component
public class PaymentFallbackServiceImpl implements PaymentFallbackService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id)
    {
        return new CommonResult<>(44444,"服务降级返回,---PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
