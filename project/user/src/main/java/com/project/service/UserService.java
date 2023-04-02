package com.project.service;

import com.project.dao.UserDao;
import com.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JavaMailSender mailSender;
    private Map<String,String> map = new HashMap<>();
    public void getcode(HttpServletRequest request){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("您本次的验证码是:");
        String code = String.valueOf((int)(Math.random()*1000000));
        message.setText("尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + code + ",（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）");
        String stuId = request.getParameter("stuId");
        map.put(stuId,code);
        message.setFrom("1149322459@qq.com");
        String toMail = stuId + "@mail.sustech.edu.cn" ;
        message.setTo(toMail);
        mailSender.send(message);
    }
    public int register(HttpServletRequest request){
        Integer stuId = Integer.valueOf(request.getParameter("stuId"));
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        User user = new User();
        String code = map.get(request.getParameter("stuId"));
        String code2 = request.getParameter("code");
        if(!code2.equals(code)){
            return 1;
        }
        User user1 = userDao.getUser2(stuId);
        if(user1 != null){
            return 2;
        }
        user.setStuId(stuId);
        user.setPassword(password);
        user.setName(name);
        user.setCredit(100);
        userDao.addUser(user);
        return 3;
    }
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
