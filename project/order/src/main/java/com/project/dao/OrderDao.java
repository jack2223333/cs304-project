package com.project.dao;

import com.project.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("insert into order values (#{orderId},#{client},#{server},#{date},#{description},#{reward},#{type},#{status})")
    void add(Order order);

    @Select("select * from order where client = #{client} and status = #{status}")
    List<Order> getByUserAndStatus(Integer client,Integer status);

    @Select("select * from order where server = #{server} and status = #{status}")
    List<Order> getByUserAndStatus2(Integer server,Integer status);

    @Select("select * from order where type = #{type} and status = #{status}")
    List<Order> getByTypeAndStatus(Integer type,Integer status);


}
