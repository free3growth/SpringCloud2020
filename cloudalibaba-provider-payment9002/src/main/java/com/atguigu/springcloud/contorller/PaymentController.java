package com.atguigu.springcloud.contorller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: bin.wang
 * @date: 2020/7/17 13:29
 */
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPost;

    @GetMapping(value = "/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "Nacos registry ,serverPort:" + serverPost+" \t id:"+id;
    }
}
