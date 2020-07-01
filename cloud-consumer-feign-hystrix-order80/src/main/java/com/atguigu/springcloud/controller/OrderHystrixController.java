package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: bin.wang
 * @date: 2020/7/1 13:38
 */
@RestController
@Slf4j
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String payment_OK(@PathVariable("id") Integer id){
       return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(
            fallbackMethod = "paymentInfo_TimeOutHandler",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    public String payment_timeout(@PathVariable("id") Integer id){
//        //自己的程序出错
//        int age=10/0;
        return paymentHystrixService.paymentInfo_timeout(id);
    }

    public  String paymentInfo_TimeOutHandler(Integer id){
        return "我是消费者80，对方支付系统繁忙，请10秒后再试或者自己运行出错请检查自己┭┮﹏┭┮";
    }
}
