package com.atguigu.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

/**
 * @author: bin.wang
 * @date: 2020/7/23 11:18
 */
public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(4444, "按客户自定义，global handlerException---------1");
    }
    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(4444, "按客户自定义，global handlerException---------2");
    }
}
