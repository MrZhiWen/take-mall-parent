package com.mall.member.controller;

import com.mall.common.commons.bean.ResultVO;
import com.mall.common.module.member.bean.SysUser;
import com.mall.common.module.order.api.OrderServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : DubboServerController.java
 * @Description : dubbo 接口测试 控制类
 * @Author : lizhiwen
 * @Date: 2020-09-10 14:44
 */
@RestController
@RequestMapping("/dubbo")
@Slf4j
@Api(value = "dubbo 接口测试控制类", tags = {"dubbo 接口测试"})
public class DubboServerController {
    @Reference
    private OrderServiceApi orderServiceApi;


    @ApiOperation(value = "dubbo 调用获取 信息", notes = "提示内容")
    @GetMapping("/getInfo")
    public ResultVO getInfo(){
        String str = orderServiceApi.getInfo();
        return ResultVO.success("成功", str);
    }
}
