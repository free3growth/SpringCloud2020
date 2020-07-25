package com.atguigu.springcloud.domain;

import lombok.Data;

/**
 * @author: bin.wang
 * @date: 2020/7/24 15:01
 */
@Data
public class Storage {
    private Long id;

    // 产品id
    private Long productId;

    //总库存
    private Integer total;


    //已用库存
    private Integer used;


    //剩余库存
    private Integer residue;

}
