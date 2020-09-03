package com.mall.member.controller;

import com.mall.common.commons.bean.ResultVO;
import com.mall.member.config.RocketMqConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : ConfigTestController.java
 * @Description : 测试 配置参数 是否生效
 * @Author : lizhiwen
 * @Date: 2020-09-03 10:23
 */
@RestController
@RequestMapping("/config")
@Slf4j
@Api(value = "获取配置参数 相关接口", tags = {"获取配置参数"})
public class ConfigTestController {
    @Autowired
    private RocketMqConfig rocketMqConfig;


    @ApiOperation("获取配置参数")
    @GetMapping("/getConfig")
    public ResultVO getConfig(){
        return ResultVO.success("成功",rocketMqConfig.getOrderReceiveGroup());
    }
}
