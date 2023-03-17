package com.project.controller;

import com.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 发起订单，订单类型，1表示帮带外卖，2表示帮作业，3表示跳蚤市场，12都需要支付钱，3是得钱
     * 用户发起12订单的时候需要先判断用户余额是否充足，再扣钱
     */
    @PostMapping("/add")
    public Result addOrder(HttpServletRequest request){
        return orderService.addOrder(request) ? new Result(null,200,"下单成功") : new Result(null,400,"下单失败");
    }
    /**
     * 用户根据订单号删除订单
     */
    @DeleteMapping("/delete")
    public Result deleteOrder(HttpServletRequest request){
        return null;
    }
    /**
     * 修改订单状态，比如，一个订单最开始是未被接，被接了之后就变成2状态，在用户确认已经结束订单时订单变成3状态
     */
    @PostMapping("/update")
    public Result updateOrder(HttpServletRequest request){
        return null;
    }
    /**
     * 通过类型和订单状态查找订单，比如
     * 在接单页面上，显示的应该是未被接的订单，用户通过类型筛选，可以选出3类不同的可以接的单
     * 在用户个人中心，可以显示已经完成的历史订单，即状态为3的订单，也可以显示正在进行的
     */
    public Result getByTypeAndStatus(HttpServletRequest request){
        return null;
    }
}
