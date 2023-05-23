package com.project.controller;

import com.project.domain.Evaluation;
import com.project.service.EvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/eval")
@CrossOrigin
public class EvalController {
    @Autowired
    private EvalService evalService;
    @PostMapping("/add")
    public Result addEval(HttpServletRequest request,@RequestParam(value = "file",required = false) MultipartFile file){
        System.out.println(request.getParameter("text"));
        return evalService.addEval(request,file) ? new Result(null,200,"评论成功") : new Result(null,400,"评论失败");
    }
    @GetMapping("/get")
    public Result getEval(HttpServletRequest request){
        List<Evaluation> e = evalService.getEval(request);
        boolean flag = e != null && e.size() > 0 ;
        return flag ? new Result(e,200,"查询成功") : new Result(null,400,"查询失败");
    }
}
