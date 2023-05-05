package com.project.service;

import com.project.dao.OrderDao;
import com.project.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    private Integer orderId = 1;
    public boolean addOrder(HttpServletRequest request){
        Order order = new Order();
        order.setOrderId(orderId++);
        order.setClient(Integer.valueOf(request.getParameter("client")));
        order.setServer(Integer.valueOf(request.getParameter("server")));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date now=new Date();
        order.setDate(sdf.format(now));
        order.setDescription(request.getParameter("description"));
        order.setReward(Integer.valueOf(request.getParameter("reward")));
        order.setType(Integer.valueOf(request.getParameter("type")));
        order.setStatus(1);
        orderDao.add(order);
        return true;
    }
}
