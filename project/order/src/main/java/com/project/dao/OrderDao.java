package com.project.dao;

import com.project.domain.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDao {
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    @Insert("insert into orders(client_id,server_id,pub_date,description,reward,task_type,status) values (#{client},null,#{date},#{description},#{reward},#{type},#{status})")
    void add(Order order);

    @Select("select money from users where client = #{client}")
    double getMoneyById(Integer client);

    @Select("select * from order where client = #{client} and status = #{status}")
    List<Order> getByClientAndStatus(Integer client,Integer status);

    @Select("select * from order where server = #{server} and status = #{status}")
    List<Order> getByServerAndStatus(Integer server,Integer status);

    @Select("select * from order where type = #{type} and status = #{status}")
    List<Order> getByTypeAndStatus(Integer type,Integer status);

    @Update("update orders set status = status + 1 where order_id= #{id}")
    void modifyStatus(Integer id);

    @Select("select * from orders where order_id= #{id}")
    List<Order> getStatusById(Integer id);

    @Delete("delete from orders where order_id= #{id}")
    void delete(Integer id);
}
