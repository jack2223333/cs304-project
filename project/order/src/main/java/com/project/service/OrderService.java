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
        order.setClient_id(client);
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = fmt.parse(request.getParameter("date"));
        order.setPub_date(date);
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
        order.setTask_type(task_type);
        order.setStatus(1);
        String label=request.getParameter("label");
        order.setLabel(label);
        String picture=request.getParameter("picture");
        order.setPicture(picture);
        String title=request.getParameter("title");
        order.setTitle(title);
        orderDao.add(order);
        return true;
    }
    public List<Order> allItemByType(HttpServletRequest request){
        Integer task_type= Integer.valueOf(request.getParameter("type"));
        return orderDao.getByTypeAndStatus(task_type,1);
    }
    public List<Order> allItemByTypeAndLabel(HttpServletRequest request){
        Integer task_type= Integer.valueOf(request.getParameter("type"));
        String label= request.getParameter("label");
        String order= request.getParameter("order");
        if (order.equals("金钱升序")){
            return orderDao.getByTypeAndLabelAndStatusMoneyIn(task_type,label,1);
        } else if(order.equals("金钱降序")){
            return orderDao.getByTypeAndLabelAndStatusMoneyDe(task_type,label,1);
        }else if(order.equals("")){
            return orderDao.getByTypeAndLabelAndStatus(task_type,label,1);
        }
        return null;
    }
    public List<Order> allServedOrderFromClient(HttpServletRequest request){
        Integer client= Integer.valueOf(request.getParameter("client"));
        Integer task_status= Integer.valueOf(request.getParameter("status"));
        return orderDao.getByClientAndStatus(client,task_status);
    }
    public List<Order> allTakeOrderFromServer(HttpServletRequest request){
        Integer server= Integer.valueOf(request.getParameter("server"));
        Integer status= Integer.valueOf(request.getParameter("status"));
        return orderDao.getByServerAndStatus(server,status);
    }
    public boolean takeOrder(HttpServletRequest request){
        Integer type= Integer.valueOf(request.getParameter("type"));
        Integer server= Integer.valueOf(request.getParameter("server"));
        Integer client= Integer.valueOf(request.getParameter("client"));
        Double reward= Double.valueOf(request.getParameter("reward"));
        Integer id= Integer.valueOf(request.getParameter("orderId"));
        if(type==3){
            double leftMoney=orderDao.getMoneyById(server);
            if (leftMoney<reward){
                return false;
            }else {
                orderDao.reduceMoney(server,reward);
                orderDao.increaseMoney(client,reward);
            }
        }
        orderDao.modifyStatusToTwo(id);
        return true;
    }
    public void completeOrder(HttpServletRequest request){
        Integer type= Integer.valueOf(request.getParameter("type"));
        Integer server= Integer.valueOf(request.getParameter("server"));
        Integer client= Integer.valueOf(request.getParameter("client"));
        Double reward= Double.valueOf(request.getParameter("reward"));
        Integer id= Integer.valueOf(request.getParameter("orderId"));
        if(type==1 || type==2){
            orderDao.reduceMoney(client,reward);
            orderDao.increaseMoney(server,reward);
        }
        orderDao.modifyStatusToThree(id);
    }
    public Integer modifyStatus(HttpServletRequest request){
        Integer id= Integer.valueOf(request.getParameter("orderId"));
//        orderDao.modifyStatus(id);
        return orderDao.getStatusById(id,id).get(0).getStatus();
    }
    public void deleteOrder(Integer id){
        orderDao.delete(id);
    }

    //辅助方法，判断某人是否有权限删除订单
    public boolean checkIdentity(Integer orderId,Integer clientId){
        return orderDao.getStatusById(orderId, clientId).size() != 0;
    }
}
