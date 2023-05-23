package com.project.service;

import com.project.dao.EvalDao;
import com.project.domain.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EvalService {
    @Autowired
    private EvalDao evalDao ;

    public boolean addEval(HttpServletRequest request, MultipartFile file) {
        System.out.println(request);
        String fileName = file.getOriginalFilename();
        String path = "D:\\project\\react\\frontend\\public\\eval\\" + fileName;
        boolean flag = true;
        try {
            file.transferTo(new File(path));
            Evaluation evaluation = new Evaluation();
            evaluation.setText(request.getParameter("text"));
            evaluation.setPoint(Double.valueOf(request.getParameter("point")));
            evaluation.setOrderId(Integer.valueOf(request.getParameter("orderId")));
            evaluation.setClient(Integer.valueOf(request.getParameter("client")));
            evaluation.setServer(Integer.valueOf(request.getParameter("server")));
            evaluation.setPic(path);
            evalDao.addEval(evaluation);
        } catch (IOException e) {
            flag = false;
            throw new RuntimeException(e);
        }
        return flag;
    }
    public List<Evaluation> getEval(HttpServletRequest request){
        return evalDao.getEval(Integer.valueOf(request.getParameter("server")));
    }

}
