package com.atguigu.springcloud.service;

/**
 * @author: bin.wang
 * @date: 2020/7/24 15:05
 */
public interface StorageService {



    /**
     * 扣减库存
     * @param productId
     * @param count
     */
    void decrease(Long productId, Integer count);

}
