package com.mall.member.controller;

import com.mall.common.commons.bean.ResultVO;
import com.mall.member.bean.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : MongoTestController.java
 * @Description : MongoDB 连接操作 测试控制类
 * @Author : lizhiwen
 * @Date: 2020-09-02 14:44
 */
@RestController
@RequestMapping("/mongo")
@Slf4j
@Api(value = "mongo 操作 相关接口", tags = {"mongo测试操作"})
public class MongoTestController {
    @Resource
    private MongoTemplate mongoTemplate;

    @ApiOperation("添加数据")
    @GetMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名", required = true),
            @ApiImplicitParam(name = "age",value = "年龄", required = true),
            @ApiImplicitParam(name = "address",value = "用户地址")
    })
    public ResultVO save(@RequestParam(name = "name") String name,
                         @RequestParam(name = "age") Integer age,
                         @RequestParam(name = "address") String address){
        User userVo = User.builder().name(name).age(age).address(address).build();
        User user = mongoTemplate.save(userVo,"user");
        return ResultVO.success("成功",user);
    }

    @ApiOperation("查询获取数据")
    @GetMapping("/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名", required = true),
            @ApiImplicitParam(name = "age",value = "年龄"),
            @ApiImplicitParam(name = "address",value = "用户地址")
    })
    public ResultVO query(@RequestParam(name = "name") String name,
                         @RequestParam(name = "age") Integer age,
                         @RequestParam(name = "address") String address){

        Query query = new Query();
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (age != null) {
            query.addCriteria(Criteria.where("age").is(age));
        }
        if (address != null || StringUtils.isNotEmpty(address)) {
            query.addCriteria(Criteria.where("address").is(address));
        }
       List<User> list = mongoTemplate.find(query,User.class,"user");
        return ResultVO.success("成功",list);
    }

    @ApiOperation("修改数据")
    @GetMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名", required = true),
            @ApiImplicitParam(name = "age",value = "年龄", required = true),
            @ApiImplicitParam(name = "address",value = "用户地址")
    })
    public ResultVO update(@RequestParam(name = "name") String name,
                          @RequestParam(name = "age") Integer age,
                          @RequestParam(name = "address") String address){

        Query query = new Query();
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        Update update = new Update();
        if (age != null) {
            update.set("age",age);
        }
        if (address != null || StringUtils.isNotEmpty(address)) {
            update.set("address",address);
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,User.class,"user");
        return ResultVO.success("成功",updateResult);
    }
    @ApiOperation("删除数据")
    @GetMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名", required = true),
            @ApiImplicitParam(name = "age",value = "年龄"),
            @ApiImplicitParam(name = "address",value = "用户地址")
    })
    public ResultVO delete(@RequestParam(name = "name") String name,
                          @RequestParam(name = "age") Integer age,
                          @RequestParam(name = "address") String address){

        Query query = new Query();
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (age != null) {
            query.addCriteria(Criteria.where("age").is(age));
        }
        if (address != null || StringUtils.isNotEmpty(address)) {
            query.addCriteria(Criteria.where("address").is(address));
        }
        DeleteResult deleteResult = mongoTemplate.remove(query,User.class,"user");
        return ResultVO.success("成功",deleteResult);
    }

}
