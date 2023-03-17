package com.project.service;

import com.project.dao.UserDao;
import com.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public boolean login(Integer stuId,String password){
        User u = userDao.getUser(stuId,password);
        return u != null;
    }
    public boolean setPwd(Integer stuId,String oldPwd,String newPwd){
        return userDao.setPwd(stuId,oldPwd,newPwd) == 1;
    }
    public boolean setMoney(Integer money,Integer stuId,Integer type){
        if(type == 1){
            return userDao.setMoney1(money, stuId) == 1;
        }else{
            int m = userDao.getMoney(stuId);
            if(m<money){
                return false;
            }else{
                return userDao.setMoney2(money, stuId) == 1;
            }
        }
    }
}
