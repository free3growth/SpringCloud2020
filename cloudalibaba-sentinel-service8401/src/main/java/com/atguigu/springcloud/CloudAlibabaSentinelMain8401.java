package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: bin.wang
 * @date: 2020/7/22 14:19
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaSentinelMain8401 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaSentinelMain8401.class, args);
    }
}
