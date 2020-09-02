package com.mall.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.commons.bean.ResultVO;
import com.mall.common.module.member.bean.SysUser;
import com.mall.common.module.member.enums.OnOff;
import com.mall.common.module.member.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : MybatisPlusTestController.java
 * @Description : Mybatis plus 测试连接操作
 * @Author : lizhiwen
 * @Date: 2020-09-02 15:01
 */
@RestController
@RequestMapping("/mybatis")
@Slf4j
@Api(value = "mybatis 操作 相关接口", tags = {"mybatis 测试操作"})
public class MybatisPlusTestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 测试 插入 组件生成策略
     * pojo 主键设置 @TableId(value = "user_id", type = IdType.AUTO)
     * pojo 处理字段不一致 @TableField(value = "id")  不存在字段 @TableField(exist = false)
     */
    @ApiOperation("添加数据")
    @PostMapping("/insert")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名称", required = true),
            @ApiImplicitParam(name = "limitedIp",value = "限制允许登录的IP集合", required = true),
            @ApiImplicitParam(name = "limitMultiLogin",value = "是否允许账号同一个时刻多人在线 Y/N", required = true),
            @ApiImplicitParam(name = "loginName",value = "登录名", required = true),
            @ApiImplicitParam(name = "password",value = "登录密码", required = true),
            @ApiImplicitParam(name = "valid",value = "软删除标识，Y/N", required = true)
    })
    public ResultVO insert(@RequestParam(name = "userName") String userName,
                           @RequestParam(name = "limitedIp") String limitedIp,
                           @RequestParam(name = "limitMultiLogin") String limitMultiLogin,
                           @RequestParam(name = "loginName") String loginName,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "valid") String valid) {
        SysUser sysUser = SysUser.builder().userName(userName)
                .limitedIp(limitedIp)
                .limitMultiLogin(OnOff.getEnumByCode(limitMultiLogin))
                .loginName(loginName)
                .password(password)
                .valid(valid)
                .lastChangePwdTime(LocalDateTime.now())
//                .createTime(LocalDateTime.now())
//                .updateTime(LocalDateTime.now())
                .build();
        int insert = sysUserMapper.insert(sysUser);
        System.out.println("insert => " + insert);
        System.out.println("sysUser id info " + sysUser.getUserId());
        return ResultVO.success("成功",sysUser.getUserId());
    }

    /**
     * 测试 主键查询
     */
    @ApiOperation("查询数据")
    @PostMapping("/selectById")
    @ApiImplicitParam(name = "userId",value = "用户Id", required = true)
    public ResultVO selectById(@RequestParam(name = "userId") Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        System.out.println("selectById result " + sysUser);
        return ResultVO.success("成功",sysUser);
    }

    /**
     * 测试 主键更新
     */
    @ApiOperation("添加数据")
    @PostMapping("/updateById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id", required = true),
            @ApiImplicitParam(name = "userName",value = "用户名称", required = true),
            @ApiImplicitParam(name = "limitedIp",value = "限制允许登录的IP集合", required = true),
            @ApiImplicitParam(name = "limitMultiLogin",value = "是否允许账号同一个时刻多人在线 Y/N", required = true),
            @ApiImplicitParam(name = "loginName",value = "登录名", required = true),
            @ApiImplicitParam(name = "password",value = "登录密码", required = true),
            @ApiImplicitParam(name = "valid",value = "软删除标识，Y/N", required = true)
    })
    public ResultVO updateById(@RequestParam(name = "userId") Long userId,
                       @RequestParam(name = "userName") String userName,
                       @RequestParam(name = "limitedIp") String limitedIp,
                       @RequestParam(name = "limitMultiLogin") String limitMultiLogin,
                       @RequestParam(name = "loginName") String loginName,
                       @RequestParam(name = "password") String password,
                       @RequestParam(name = "valid") String valid) {
        SysUser sysUser = SysUser.builder().userName(userName)
                .limitedIp(limitedIp)
                .limitMultiLogin(OnOff.getEnumByCode(limitMultiLogin))
                .loginName(loginName)
                .password(password)
                .valid(valid)
                .lastChangePwdTime(LocalDateTime.now())
//                .createTime(LocalDateTime.now())
//                .updateTime(LocalDateTime.now())
                .userId(userId)
                .build();
        int i = sysUserMapper.updateById(sysUser);
        System.out.println("updateById result " + i);
        System.out.println("updateById  " + sysUser);
        return ResultVO.success("成功",sysUser);
    }

    /**
     * 测试 条件更新
     * eq //相等 user_id = 3
     * ne // 不等于 <>
     * gt //大于 >
     * ge //大于等于 >=
     * lt //小于 <
     * le //小于等于 <=
     * 详细条件说明 https://baomidou.com/guide/wrapper.html
     */
    @ApiOperation("添加数据")
    @PostMapping("/whereUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id", required = true),
            @ApiImplicitParam(name = "valid",value = "软删除标识，Y/N", required = true)
    })
    public ResultVO whereUpdate(@RequestParam(name = "userId") Long userId,
                            @RequestParam(name = "valid") String valid) {
        /**
         * QueryWrapper
         */
        SysUser sysUser = SysUser.builder().valid(valid)
                .build();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId); //相等 user_id = 3
//        wrapper.ne("user_id",3); //不等于 user_id <> 3
        int update = sysUserMapper.update(sysUser, wrapper);
        /**
         * UpdateWrapper
         * 数据库字段的名字
         */
//        UpdateWrapper<SysUser> wrapper1 = new UpdateWrapper<>();
//        wrapper1.set("user_name","李志文1")
//                .eq("user_id",3);
//        int update = sysUserMapper.update(null, wrapper1);

        System.out.println("update result " + update);
        System.out.println("update  " + JSONObject.toJSONString(sysUser));
        return ResultVO.success("成功",sysUser);
    }

    /**
     * 测试 分页查询
     */
    @ApiOperation("分页查询数据")
    @PostMapping("/selectPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "第几页", required = true),
            @ApiImplicitParam(name = "size",value = "每页大小", required = true)
    })
    public ResultVO selectPage(@RequestParam("current") Integer current,
                           @RequestParam("size") Integer size){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Page<SysUser> page = new Page<>(current,size);
        //查询 第一页的 每页一条数据
        Page<SysUser> sysUserPage = sysUserMapper.selectPage(page, null);
        System.out.println(JSONObject.toJSONString(sysUserPage));
        return ResultVO.success("成功",sysUserPage);
    }

    /**
     * 测试 查询list
     */
    @ApiOperation("查询list 数据")
    @GetMapping("/selectList")
    public ResultVO selectList(){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //查询 第一页的 每页一条数据
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(JSONObject.toJSONString(sysUsers));
        return ResultVO.success("成功",sysUsers);
    }

    /**
     * 测试 删除数据 软删除(逻辑删除)
     */
    @ApiOperation("删除数据 软删除(逻辑删除)")
    @PostMapping("/delete")
    @ApiImplicitParam(name = "userId",value = "用户 ID", required = true)
    public ResultVO delete(@RequestParam("userId") Long userId){

        //查询 第一页的 每页一条数据
        int i = sysUserMapper.deleteById(userId);
        System.out.println("deleteById result " + i);
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(JSONObject.toJSONString(sysUsers));
        return ResultVO.success("成功",sysUsers);
    }

//
//    /**
//     * 测试 自定义查询方法
//     */
//
//    public void testMapper(){
//        SysUser sysUser = sysUserMapper.selectFindById("3");
//        System.out.println(JSONObject.toJSONString(sysUser));
//    }

}
