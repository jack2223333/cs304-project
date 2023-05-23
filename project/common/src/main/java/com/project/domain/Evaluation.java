package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Evaluation {
    private Double point; //评分
    private String text; // 评价文字
    private String pic; //图片
    private Integer orderId; //所评价的订单号
    private Integer client; //学生号
    private Integer server; //学生号
}
