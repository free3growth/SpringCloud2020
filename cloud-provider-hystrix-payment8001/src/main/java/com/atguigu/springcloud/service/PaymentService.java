package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")})
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
}
