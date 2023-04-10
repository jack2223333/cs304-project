package com.project.service;

import com.project.dao.OrderDao;
import com.project.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    public boolean addOrder(HttpServletRequest request) throws ParseException {
        Order order = new Order();
        Integer client=Integer.valueOf(request.getParameter("client"));
        order.setClient(client);
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(request.getParameter("date"));
        order.setDate(date);
        order.setDescription(request.getParameter("description"));
        int reward= Integer.parseInt(request.getParameter("reward"));
        order.setReward(reward);
        int task_type= Integer.parseInt(request.getParameter("type"));
        if(task_type==1 || task_type==2){
            double leftMoney=orderDao.getMoneyById(client);
            if(leftMoney<reward){
                return false;
            }
        }
        order.setType(task_type);
        order.setStatus(1);
        orderDao.add(order);
        return true;
    }
    public List<Order> allNonserverOrder(HttpServletRequest request){
        Integer task_type= Integer.valueOf(request.getParameter("type"));
        return orderDao.getByTypeAndStatus(task_type,1);
    }
    public List<Order> allServedOrderFromClient(HttpServletRequest request){
        Integer client= Integer.valueOf(request.getParameter("client"));
        Integer status= Integer.valueOf(request.getParameter("status"));
        return orderDao.getByClientAndStatus (client,status);
    }
    public List<Order> allTakeOrderFromServer(HttpServletRequest request){
        Integer server= Integer.valueOf(request.getParameter("server"));
        Integer status= Integer.valueOf(request.getParameter("status"));
        return orderDao.getByServerAndStatus(server,status);
    }
    public Integer modifyStatus(HttpServletRequest request){
        Integer id= Integer.valueOf(request.getParameter("orderId"));
        orderDao.modifyStatus(id);
        return orderDao.getStatusById(id).get(0).getStatus();
    }
    public boolean deleteOrder(Integer id){
        if(orderDao.getStatusById(id).size()!=0){
            orderDao.delete(id);
            return true;
        }
        return false;
    }
}
