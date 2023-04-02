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
    private Integer credit; //用户信誉度
}
