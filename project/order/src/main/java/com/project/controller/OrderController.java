package com.project.controller;

import com.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 发起订单，订单类型，1表示帮带外卖，2表示帮作业，3表示跳蚤市场，12都需要支付钱，3是得钱
     * 用户发起12订单的时候需要先判断用户余额是否充足，再扣钱
     */
    @PostMapping("/add")
    public Result addOrder(HttpServletRequest request) throws ParseException {
        return orderService.addOrder(request) ? new Result(null,200,"下单成功") : new Result(null,400,"下单失败");
    }
    /**
     * 用户根据订单号删除订单
     */
    @DeleteMapping("/delete")
    public Result deleteOrder(HttpServletRequest request){
        int status= Integer.parseInt(request.getParameter("status"));
        Integer id= Integer.valueOf(request.getParameter("orderId"));
        Integer client_id= Integer.valueOf(request.getParameter("client"));
        if (orderService.checkIdentity(id,client_id)){
            if(status==2){
                return new Result(null,400,"已被接单，不可取消");
            }else if(status==3){
                return new Result(null,400,"订单已完成，不可取消");
            }else {
                orderService.deleteOrder(id);
                return new Result(null,200,"成功取消订单");
            }
        }else {
            return new Result(null,400,"该用户不是发布者，没有权限删除");
        }
    }
    /**
     * 修改订单状态，比如，一个订单最开始是未被接，被接了之后就变成2状态，在用户确认已经结束订单时订单变成3状态
     */
    @PostMapping("/takeorder")
    public Result takeOrder(HttpServletRequest request){
        if(orderService.takeOrder(request)){
            return new Result(null,200,"成功接单");
        }
        return new Result(null,400,"余额不足，购买失败");
    }
    @PostMapping("/completeorder")
    public Result completeOrder(HttpServletRequest request){
        orderService.completeOrder(request);
        return new Result(null,200,"订单完成");
    }
    /**
     * 修改订单基本信息，比如，金额，描述等
     */
    @PostMapping("/updateinfo")
    public Result updateOrderInfo(HttpServletRequest request){
        Integer status=orderService.modifyStatus(request);
        if(status==3){
            autoRedirect();
        }
        return new Result(null,200,"订单状态修改成功");
    }
    /**
     *订单结束后自动跳转到评论页面，未测试
     */
    @GetMapping("/autoRedirect")
    public RedirectView autoRedirect() {
        // 重定向到 page2 页面
        return new RedirectView("");
    }
    /**
     * 通过类型和订单状态查找订单，比如
     * 在接单页面上，显示的应该是未被接的订单，用户通过类型筛选，可以选出3类不同的可以接的单
     * 在用户个人中心，可以显示已经完成的历史订单，即状态为3的订单，也可以显示正在进行的
     */
    @PostMapping("/select/bytype")
    public Result getNonserverByTypeAndStatus(HttpServletRequest request){
        return new Result(orderService.allItemByType(request),200,"筛选成功");
    }
    @PostMapping("/select/bylabel")
    public Result getNonserverByTypeAndLabelAndStatus(HttpServletRequest request){
        return new Result(orderService.allItemByTypeAndLabel(request),200,"筛选成功");
    }
    @PostMapping("/select/client")
    public Result getServedOrderByTypeAndStatus(HttpServletRequest request){
        return new Result(orderService.allServedOrderFromClient(request),200,"筛选成功");
    }
    @PostMapping("/select/server")
    public Result getByTypeAndStatus(HttpServletRequest request){
        return new Result(orderService.allTakeOrderFromServer(request),200,"筛选成功");
    }
    @PostMapping("/info")
    public Result getProductInfo(HttpServletRequest request){
        return new Result(orderService.checkOrder(request),200,"展示成功");
    }
}
