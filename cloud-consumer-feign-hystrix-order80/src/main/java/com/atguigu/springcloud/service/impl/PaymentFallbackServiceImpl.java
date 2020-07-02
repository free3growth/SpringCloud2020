package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

/**
 * @author: bin.wang
 * @date: 2020/7/2 10:23
 */
@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-----PaymentFallbackServiceImpl fallback paymentInfo_OK,^_^";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "-----PaymentFallbackServiceImpl fallback paymentInfo_timeout,^o(╥﹏╥)o";
    }
}
