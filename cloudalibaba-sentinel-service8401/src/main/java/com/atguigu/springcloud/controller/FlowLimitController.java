package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: bin.wang
 * @date: 2020/7/22 14:17
 */
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public  String testA(){
        return "---------------TestA";
    }

    @GetMapping("/testB")
    public  String testB(){
        log.info(Thread.currentThread().getName()+"/t"+"....testB");
        return "---------------TestB";
    }

//    /**
//     * RT 测试
//     * @return
//     */
//    @GetMapping("/testD")
//    public String testD()
//    {
//        //暂停几秒钟线程
//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
//        log.info("testD 测试RT");
//        return "------testD";
//    }

    /**
     * 异常比例测试
     * @return
     */
    @GetMapping("/testD")
    public String testD()
    {

        log.info("testD 测试异常比例");
        int age = 10/0;
        return "------testD";
    }


    /**
     * 异常总数
     * @return
     */
    @GetMapping("/testE")
    public String testE()
    {
        log.info("testE 测试异常数");
        int age = 10/0;
        return "------testE 测试异常数";
    }



}
