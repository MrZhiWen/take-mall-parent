package com.mall.member;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.utils.RedisKeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName : RedisTest.java
 * @Description : Redis 测试类
 * @Author : lizhiwen
 * @Date: 2020-09-01 16:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
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
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;


    /**
     * 获取
     */
    @Test
    public void getKey() {
        String value =  redisTemplate.opsForValue().get("13");
        System.out.println(value);
    }

    /**
     * 添加值
     */
    @Test
    public void set() {
        redisTemplate.opsForValue().set("user:123:card","123");
    }

    /**
     * newKey不存在时才重命名
     */
    @Test
    public void renameKeyNotExist() {
        boolean flay = redisTemplate.renameIfAbsent("oldKey", "newKey");
        System.out.println(flay);
    }

    /**
     * 删除key
     */
    @Test
    public void deleteKey() {
        redisTemplate.delete("key");
    }

    /**
     * 删除多个key
     */
    @Test
    public void deleteKey1() {
        String[] keys = {"123","213"};
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     */
    @Test
    public void deleteKey2() {
        Collection<String> keys = Arrays.asList("123","123");
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期
     */
    @Test
    public void expireKey() {
        redisTemplate.expire("key", 50, TimeUnit.SECONDS);
    }
    public void expireKey(String key ,long timeout,TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     */
    @Test
    public void expireKeyAt() {
        Date now = new Date();
        redisTemplate.expireAt("key",new Date(now .getTime() + 60000));
    }

    /**
     * 查询key的生命周期

     */
    @Test
    public void getKeyExpire() {
       long value = redisTemplate.getExpire("key", TimeUnit.SECONDS);
        System.out.println(value);
    }

    /**
     * 将key设置为永久有效
     */
    @Test
    public void persistKey() {
        redisTemplate.persist("key");
    }




    @Test
    public void testObj(){
        UserVO userVo = new UserVO();
        userVo.setAddress("上海");
        userVo.setName("测试dfas");
        userVo.setAge(123);
        template.opsForValue().set("123",userVo);
//        expireKey("name",20, TimeUnit.SECONDS);
        UserVO result = (UserVO) template.opsForValue().get("123");
        System.out.println(JSONObject.toJSONString(result));
    }
//
//    @Test
//    public void testValueOption( )throws  Exception{
//        UserVo userVo = new UserVo();
//        userVo.setAddress("上海");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        valueOperations.set("test",userVo);
//
//        System.out.println(valueOperations.get("test"));
//    }
//
//    @Test
//    public void testSetOperation() throws Exception{
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        UserVo auserVo = new UserVo();
//        auserVo.setAddress("n柜昂周");
//        auserVo.setName("antent");
//        auserVo.setAge(23);
//        setOperations.add("user:test",userVo,auserVo);
//        Set<Object> result = setOperations.members("user:test");
//        System.out.println(result);
//    }
//
//    @Test
//    public void HashOperations() throws Exception{
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        hashOperations.put("hash:user",userVo.hashCode()+"",userVo);
//        System.out.println(hashOperations.get("hash:user",userVo.hashCode()+""));
//    }
//
//    @Test
//    public void  ListOperations() throws Exception{
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
////        listOperations.leftPush("list:user",userVo);
////        System.out.println(listOperations.leftPop("list:user"));
//        // pop之后 值会消失
//        System.out.println(listOperations.leftPop("list:user"));
//    }

}
class UserVO implements Serializable {

    public  static final String Table = "t_user";

    private String name;
    private String address;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}