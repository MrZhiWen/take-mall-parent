package com.mall.member.controller;

import com.mall.common.commons.bean.ResultVO;
import com.mall.member.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RedisTestController.java
 * @Description : Redis 测试操作 控制类
 * @Author : lizhiwen
 * @Date: 2020-09-02 14:00
 */
@RestController
@RequestMapping("/redis")
@Slf4j
@Api(value = "Redsi 操作 相关接口", tags = {"Redis测试操作"})
public class RedisTestController {
    /**
     * //操作字符串
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * // 操作对象
     */
    @Autowired
    private RedisTemplate<Object,Object> template;
    /**
     * RedisTemplate中定义了5种数据结构操作
     * redisTemplate.opsForValue();　　//操作字符串
     * redisTemplate.opsForHash();　　 //操作hash
     * redisTemplate.opsForList();　　 //操作list
     * redisTemplate.opsForSet();　　  //操作set
     * redisTemplate.opsForZSet();　 　//操作有序set
     *
     *
     *
     * stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
     * stringRedisTemplate.boundValueOps("test").increment(-1);//val做-1操作
     * stringRedisTemplate.opsForValue().get("test")//根据key获取缓存中的val
     * stringRedisTemplate.boundValueOps("test").increment(1);//val +1
     * stringRedisTemplate.getExpire("test")//根据key获取过期时间
     * stringRedisTemplate.getExpire("test",TimeUnit.SECONDS)//根据key获取过期时间并换算成指定单位
     * stringRedisTemplate.delete("test");//根据key删除缓存
     * stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值
     * stringRedisTemplate.opsForSet().add("red_123", "1","2","3");//向指定key中存放set集合
     * stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);//设置过期时间
     * stringRedisTemplate.opsForSet().isMember("red_123", "1")//根据key查看集合中是否存在指定数据
     * stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合
     */

    /**
     * 获取
     */
    @ApiOperation("添加键值")
    @GetMapping("/set")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "键", required = true),
            @ApiImplicitParam(name = "value",value = "值", required = true),
            @ApiImplicitParam(name = "second",value = "过期秒")
    })
    public ResultVO set(@RequestParam(name = "key") String key,
                        @RequestParam(name = "value") String value,
                        @RequestParam(name = "second") Long second) {
        if (second == null){
            redisTemplate.opsForValue().set(key,value);
        }else{
            redisTemplate.opsForValue().set(key,value,second, TimeUnit.SECONDS);
        }
        return  ResultVO.success("成功");
    }

    /**
     * 添加值
     */
    @ApiOperation("获取 key 值")
    @GetMapping("/getKey")
    @ApiImplicitParam(name = "key",value = "键", required = true)
    public ResultVO getKey(@RequestParam(name ="key") String key) {
      String value = redisTemplate.opsForValue().get(key);
      return ResultVO.success("成功",value);
    }

    @ApiOperation("键 增加 减少 操作")
    @GetMapping("/increment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "键", required = true),
            @ApiImplicitParam(name = "update",value = "操作 false 减 true 加", required = true)
    })
    public ResultVO increment(@RequestParam(name ="key") String key,
                              @RequestParam(name = "update") boolean update) {
        long value = 0L;
        if (update){
            value = redisTemplate.boundValueOps(key).increment(1);//val +1
        }else{
            value = redisTemplate.boundValueOps(key).increment(-1);//val -1
        }
        return ResultVO.success("成功",value);
    }
    @ApiOperation("删除键")
    @GetMapping("/delete")
    @ApiImplicitParam(name = "key",value = "键", required = true)
    public ResultVO delete(@RequestParam(name ="key") String key) {
       boolean flag = redisTemplate.delete(key);
        return ResultVO.success("成功",flag);
    }
    @ApiOperation("获取过期时间")
    @GetMapping("/getExpire")
    @ApiImplicitParam(name = "key",value = "键", required = true)
    public ResultVO getExpire(@RequestParam(name ="key") String key) {
        long second = redisTemplate.getExpire(key);
        return ResultVO.success("成功",second);
    }
    @ApiOperation("添加对象")
    @GetMapping("/setObject")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名", required = true),
            @ApiImplicitParam(name = "age",value = "年龄", required = true),
            @ApiImplicitParam(name = "address",value = "用户地址"),
            @ApiImplicitParam(name = "second",value = "过期秒")
    })
    public ResultVO setObject(@RequestParam(name = "name") String name,
                        @RequestParam(name = "age") Integer age,
                        @RequestParam(name = "address") String address,
                        @RequestParam(name = "second") Long second) {
        String key ="user:info";
        User userVo = User.builder().name(name).age(age).address(address).build();
        if (second == null){
            template.opsForValue().set(key,userVo);
        }else{
            template.opsForValue().set(key,userVo,second,TimeUnit.SECONDS);
        }
        User result = (User) template.opsForValue().get(key);
        return  ResultVO.success("成功",result);
    }


}
