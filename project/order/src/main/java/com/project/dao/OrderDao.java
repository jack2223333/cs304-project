package com.project.dao;

import com.project.domain.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDao {
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    @Insert("insert into orders(client_id,server_id,pub_date,description,reward,task_type,status,label,picture) values (#{client_id},null,#{pub_date},#{description},#{reward},#{task_type},#{status},#{label},#{picture} )")
    void add(Order order);
    @Select("select money from users where stu_id= #{client}")
    double getMoneyById(Integer client);
    @Update("update users set money = money - #{reward} where stu_id= #{id}")
    void reduceMoney(@Param("id")Integer id,@Param("reward")double reward);
    @Update("update users set money = money + #{reward} where stu_id= #{id}")
    void increaseMoney(@Param("id")Integer id,@Param("reward")double reward);

    @Select("select * from orders where client_id = #{client} and status=#{task_status}")
    List<Order> getByClientAndStatus(@Param("client")Integer client,@Param("task_status")Integer task_status);

    @Select("select * from orders where server_id = #{server} and status = #{status}")
    List<Order> getByServerAndStatus(@Param("server")Integer server,@Param("status")Integer status);

    @Select("select * from orders where task_type = #{task_type} and status = #{status}")
    List<Order> getByTypeAndStatus(@Param("task_type")Integer task_type,@Param("status")Integer status);
    @Select("select * from orders where task_type = #{task_type} and label = #{label} and status = #{status}")
    List<Order> getByTypeAndLabelAndStatus(@Param("task_type")Integer task_type,@Param("label")String label,@Param("status")Integer status);
    @Select("select * from orders where task_type = #{task_type} and label = #{label} and status = #{status} order by reward asc ")
    List<Order> getByTypeAndLabelAndStatusMoneyIn(@Param("task_type")Integer task_type,@Param("label")String label,@Param("status")Integer status);
    @Select("select * from orders where task_type = #{task_type} and label = #{label} and status = #{status} order by reward desc")
    List<Order> getByTypeAndLabelAndStatusMoneyDe(@Param("task_type")Integer task_type,@Param("label")String label,@Param("status")Integer status);

    @Update("update orders set status = 2 where order_id= #{id}")
    void modifyStatusToTwo(Integer id);
    @Update("update orders set status = 3 where order_id= #{id}")
    void modifyStatusToThree(Integer id);

    @Select("select * from orders where order_id= #{order} and client_id= #{client}")
    List<Order> getStatusById(@Param("order")Integer id1,@Param("client")Integer id2);

    @Delete("delete from orders where order_id= #{id}")
    void delete(Integer id);
}
