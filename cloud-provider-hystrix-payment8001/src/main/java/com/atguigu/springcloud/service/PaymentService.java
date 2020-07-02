package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author: bin.wang
 * @date: 2020/7/1 10:40
 */
@Service
public class PaymentService {
    /**
     * 正常访问，肯定OK
     * @param id
     * @return
     */
    public String paymentInfo_Ok(Integer id){
        return "***线程池："+Thread.currentThread().getName()+"   paymentInfo_OK,id:   "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * 模拟超时异常
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "paymentInfo_TimeOutHandler",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000")})
    public String paymentInfo_timeout(Integer id){
        int timeNumber=3;
        //直接报错 也会进行服务降级
//        int age=10/0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "***线程池："+Thread.currentThread().getName()+"   paymentInfo_timeout,id:   "+id+"\t"+"┭┮﹏┭┮"+" 耗时"+timeNumber+"秒钟";
    }

    public  String paymentInfo_TimeOutHandler(Integer id){
        return "***线程池："+Thread.currentThread().getName()+"   paymentInfo_TimeOutHandler,id:   "+id+"\t"+"系统繁忙或者运行繁忙，请稍后重试┭┮﹏┭┮";
    }


    //=======服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸--60%
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if ((id<0)) {
            throw  new RuntimeException("*******id 不能负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"  调用成功，流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }
}
