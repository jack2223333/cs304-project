package com.project.controller;
//统一结果返回集，后端返回给前端的统一成这个形式，data表示返回的具体数据，code表示状态码，比如两百表示操作成功，400表示操作失败，message表示状态描述信息
import lombok.*;
@ToString
@Getter
@Setter
public class Result {
    private Object data;
    private Integer code;
    private String message;
    public Result(Object data,Integer code,String message){
        this.data = data;
        this.code = code;
        this.message = message;
    }
}
