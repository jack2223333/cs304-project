package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class Order {
    private Integer order_id; //订单号
    private Integer client_id; //付钱的用户id
    private Integer server_id; //得钱的用户id
    private Date pub_date; //订单发起时间
    private String description; //订单描述信息
    private Integer reward; //悬赏金额
    private Integer task_type; //订单类型，1表示任务发布，2表示信息交流，3表示跳蚤市场
    private Integer status; //订单状态 1表示订单未被接，2表示订单已被接但没有确认完成，3表示订单已完成
    private String label; //订单大类别下小分类
    private String picture; //存储跳蚤市场商品图片地址
    private String title; //发布商品或任务标题

}
