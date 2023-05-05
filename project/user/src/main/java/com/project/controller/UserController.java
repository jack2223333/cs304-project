package com.project.controller;

import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 发送邮箱验证码
     */
    @GetMapping("/getcode")
    public Result getcode(HttpServletRequest request){
        userService.getcode(request);
        return new Result(null,200,"验证码发送成功");
    }

    /**
     * 实现邮箱验证码注册
     */
    @PostMapping("/register")
    public Result register(HttpServletRequest request){
        int a = userService.register(request);
        if(a == 1){
            return new Result(null,400,"验证码错误");
        }else if(a == 2){
            return new Result(null,300,"用户已存在");
        }
        return new Result(null,200,"注册成功");
    }
    /**
     * 实现用户登录
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request){
        Integer stuId = Integer.valueOf(request.getParameter("stuId"));
        String password = request.getParameter("password");
        return userService.login(stuId,password) ? new Result(stuId,200,"登录成功") : new Result(null,400,"登录失败");
    }
    /**
     * 实现用户修改密码
     */
    @PostMapping("/set")
    public Result setPwd(HttpServletRequest request){
        Integer stuId = Integer.valueOf(request.getParameter("stuId"));
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        return userService.setPwd(stuId,oldPwd,newPwd) ? new Result(null,200,"修改成功") : new Result(null,400,"修改失败");
    }
    /**
     * 通过邮箱找回密码
     */
    @PostMapping("/retrieve")
    public Result retrievePwd(HttpServletRequest request){
        return null;
    }
    /**
     * 修改用户余额
     */
    @PostMapping("/setMoney")
    public Result setMoney(HttpServletRequest request){
        Integer money = Integer.valueOf(request.getParameter("money"));
        Integer stuId = Integer.valueOf(request.getParameter("user"));
        Integer type = Integer.valueOf(request.getParameter("type"));//0表示付钱，1表示收钱
        return userService.setMoney(money,stuId,type) ? new Result(null,200,"修改成功") : new Result(null,400,"修改失败");
    }
}
