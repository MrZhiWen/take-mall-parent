package com.mall.oms.service;

import com.mall.common.module.order.api.OrderServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @ClassName : OrderServiceImpl.java
 * @Description : 订单接口 实现
 * @Author : lizhiwen
 * @Date: 2020-09-10 14:33
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderServiceApi {
    @Override
    public String getInfo() {
        log.info(" ... take-mall-oms ...");
        return "获取订单接口";
    }
}
