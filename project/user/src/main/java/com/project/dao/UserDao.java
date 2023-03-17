package com.project.dao;

import com.project.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    /**
     * 更改用户信息
     * @return 如果更改成功，则返回 1，否则返回 0
     */
    @Update("update user set password = #{newPwd} where stuId = #{stuId} and password = #{oldPwd}")
    Integer setPwd(Integer stuId,String oldPwd,String newPwd);
    @Update("update user set money = money + #{money} where stuId = #{stuId}")
    Integer setMoney1(Integer money,Integer stuId);

    @Update("update user set money = money - #{money} where stuId = #{stuId}")
    Integer setMoney2(Integer money,Integer stuId);

    @Select("select * from user where stuId = #{stuId} and password=#{password}")
    User getUser(Integer stuId,String password);
    @Select("select money from user where stuId = #{stuId}")
    Integer getMoney(Integer stuId);


}
