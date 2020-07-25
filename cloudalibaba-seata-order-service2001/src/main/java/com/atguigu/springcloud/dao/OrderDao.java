package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: bin.wang
 * @date: 2020/7/24 13:21
 */
@Mapper
public interface OrderDao {

    /**
     *  新建订单
     * @param order
     */
    void create(Order order);

    /**
     * 修改订单状态，从零改为1
     * @param userId
     * @param status
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
