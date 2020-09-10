package com.mall.oms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @ClassName : MongoDBTest.java
 * @Description : MongoDB 操作测试
 * @Author : lizhiwen
 * @Date: 2020-09-02 10:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 存储
     */
    @Test
    public void saveUser(){
        User user = new User();
        user.setAddress("上海");
        user.setName("测试dfas");
        user.setAge(123);
        mongoTemplate.save(user,"user");
    }

    /**
     * 条件查询
     */
    public void findUserByUserName(){

    }

    /**
     * 修改对象
     */
    public void updateUser(){

    }

    /**
     * 删除数据
     */
    public void deleteUserById(){

    }
}

class User implements Serializable {

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