package com.mall.member.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : User.java
 * @Description : 测试用 用户信息
 * @Author : lizhiwen
 * @Date: 2020-09-02 14:37
 */
@Data
@Builder
public class User implements Serializable {
    private String name;
    private String address;
    private Integer age;
}
