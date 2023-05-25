package com.project.service;

import com.alibaba.fastjson.JSON;
import com.project.dao.OrderDao;
import com.project.domain.Order;
import com.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String typeStr= request.getParameter("status");
        String[] typeList=typeStr.split(",");
        List<Order> result=new ArrayList<>();
        Integer client= Integer.valueOf(request.getParameter("client"));
        Integer type= Integer.valueOf(request.getParameter("type"));
        for (String s : typeList) {
            List<Order> cur = orderDao.getByClientAndStatus(type, client, Integer.valueOf(s));
            result.addAll(cur);
        }
        return result;
    }
    public List<Order> allTakeOrderFromServer(HttpServletRequest request){
        String typeStr= request.getParameter("status");
        String[] typeList=typeStr.split(",");
        List<Order> result=new ArrayList<>();
        Integer server= Integer.valueOf(request.getParameter("server"));
        Integer type= Integer.valueOf(request.getParameter("type"));
        for (String s : typeList) {
            List<Order> cur = orderDao.getByServerAndStatus(type, server, Integer.valueOf(s));
            result.addAll(cur);
        }
        return result;
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
        orderDao.modifyServer(id,server);
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
    public String checkOrder(HttpServletRequest request){
        Integer id= Integer.valueOf(request.getParameter("orderId"));
        Order cur=orderDao.getOrderById(id);
        User curU=orderDao.getUserById(cur.getClient_id());
        Map<String,Object> map=new HashMap<>();
        map.put("picture",cur.getPicture());
        map.put("cur.getPicture()",cur.getDescription());
        map.put("reward",cur.getReward());
        map.put("photo",curU.getPhoto());
        map.put("name",curU.getName());
        map.put("mark",curU.getMark());

        String json= JSON.toJSONString(map);
        return json;
    }


    //辅助方法，判断某人是否有权限删除订单
    public boolean checkIdentity(Integer orderId,Integer clientId){
        return orderDao.getStatusById(orderId, clientId).size() != 0;
    }
}
