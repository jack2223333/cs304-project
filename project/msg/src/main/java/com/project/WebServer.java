package com.project;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/webserver/{username}")
@Component
public class WebServer {
    public static final Map<String, Session> map = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username){
        map.put(username,session);
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username": "zhang", "username": "admin"}
            array.add(jsonObject);
        }
        for(Session s : map.values()){
            try {
                s.getBasicRemote().sendText(JSONUtil.toJsonStr(result));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @OnClose
    public void onClose(@PathParam("username") String username){
        map.remove(username);
    }
    @OnMessage
    public void onMessage(String message,Session session,@PathParam("username") String username){
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to");
        String text = obj.getStr("text");
        Session toSession = map.get(toUsername);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("from", username);
        jsonObject.set("text", text);
        try {
            toSession.getBasicRemote().sendText(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Session session,Throwable error){
        error.printStackTrace();
    }
}
