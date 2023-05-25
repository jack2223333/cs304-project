package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class User {
    private Integer stuId; //学生号
    private String password; //密码
    private String name; //自定义用户名
    private double money; //用户余额
    private String photo; //用户头像
    private double mark; //用户评分

}
