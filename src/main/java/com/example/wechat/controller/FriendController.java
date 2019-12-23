package com.example.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.wechat.entity.Friend;
import com.example.wechat.entity.Response;
import com.example.wechat.entity.Token;
import com.example.wechat.mapper.FriendMapper;
import com.example.wechat.mapper.TokenMapper;
import com.example.wechat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class FriendController {

    @Autowired
    FriendMapper friendMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    @RequestMapping("/friend")
    public Response getFriends(HttpServletRequest request){

        Response res = new Response();
        String tokenStr = request.getParameter("token");
        Token token = tokenMapper.selectUserToken(tokenStr);

        if (token == null){
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        long userId = token.getUser_id();

        List<JSONObject> list = friendMapper.select(userId);

        res.setCode(0);
        res.setData(list);
        return res;
    }
}
