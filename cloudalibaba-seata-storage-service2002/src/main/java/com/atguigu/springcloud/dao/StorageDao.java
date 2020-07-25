package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: bin.wang
 * @date: 2020/7/24 15:03
 */
@Mapper
public interface StorageDao {

    /**
     * 扣减库存信息
     * @param productId
     * @param count
     */
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
