package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class Order {
    private Integer orderId; //订单号
    private Integer client; //付钱的用户id
    private Integer server; //得钱的用户id
    private Date date; //订单发起时间
    private String description; //订单描述信息
    private Integer reward; //悬赏金额
    private Integer type; //订单类型，1表示帮带外卖，2表示帮作业，3表示跳蚤市场
    private Integer status; //订单状态 1表示订单未被接，2表示订单已被接但没有确认完成，3表示订单已完成

}
