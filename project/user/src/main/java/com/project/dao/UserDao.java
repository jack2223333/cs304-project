package com.project.dao;

import com.project.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Insert("insert into users values(#{stuId},#{password},#{name},#{credit})")
    void addUser(User user);
    /**
     * 更改用户信息
     * @return 如果更改成功，则返回 1，否则返回 0
     */
    @Update("update users set password = #{newPwd} where stuId = #{stuId} and password = #{oldPwd}")
    Integer setPwd(Integer stuId,String oldPwd,String newPwd);
    @Update("update users set money = money + #{money} where stuId = #{stuId}")
    Integer setMoney1(Integer money,Integer stuId);

    @Update("update users set money = money - #{money} where stuId = #{stuId}")
    Integer setMoney2(Integer money,Integer stuId);

    @Select("select * from users where stuId = #{stuId} and password=#{password}")
    User getUser(@Param("stuId") Integer stuId,@Param("password") String password);
    @Select("select * from users where stuId = #{stuId}")
    User getUser2(Integer stuId);
    @Select("select money from users where stuId = #{stuId}")
    Integer getMoney(Integer stuId);


}
