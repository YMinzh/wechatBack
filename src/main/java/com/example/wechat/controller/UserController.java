package com.example.wechat.controller;

import com.example.wechat.entity.Response;
import com.example.wechat.entity.Token;
import com.example.wechat.entity.User;
import com.example.wechat.mapper.TokenMapper;
import com.example.wechat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    @RequestMapping("/login")
    public Response login(HttpServletRequest request, @RequestBody User user) {
        Response res = new Response();

        String password = user.getPassword_hash();
        String username = user.getUsername();

        //判断用户名是否存在
        if (username == null) {
            res.setCode(-1);
            res.setData("用户名不能为空");
        }
        //密码是否存在
        if (password == null) {
            res.setCode(-1);
            res.setData("密码不能为空");
        }
        //从数据库获取密码
        user = userMapper.getUserByUsername(user);
        //用户不存在
        if (user == null) {
            res.setCode(-1);
            res.setData("用户不存在");
            return res;
        }

        //比对密码
        if (!user.getPassword_hash().equals(String.valueOf(password.hashCode()))) {
            res.setCode(-1);
            res.setData("密码错误");
            return res;
        }
        //校正成功
        UUID tokenValue = UUID.randomUUID();
        Token token = tokenMapper.select(user.getId());
        Token newToken = new Token();
        newToken.setUser_id(user.getId());
        newToken.setToken(tokenValue.toString());

        if (token == null) {
            tokenMapper.insert(newToken);
        } else {
            tokenMapper.update(newToken);
        }

        res.setCode(0);
        res.setData(tokenValue.toString());
        return res;
    }

    @RequestMapping("user/get")
    public Response getUser(HttpServletRequest request) {
        Response res = new Response();
        String tokenValue = request.getParameter("token");
        if (tokenValue == null) {
            res.setCode(-1);
            res.setData("没有token");
            return res;
        }

        Token token = tokenMapper.selectUserToken(tokenValue);

        if (token == null) {
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        long user_id = token.getUser_id();
        User userSelf = userMapper.getUserByUserId(user_id);

        String str = request.getParameter("id");

        if (str == null) {
            res.setCode(0);
            res.setData(userSelf);
            return res;
        }

        List<User> list = new ArrayList<User>();
        list.add(userSelf);
        long obUser_id;
        try {
            obUser_id = Long.parseLong(str);
        } catch (NumberFormatException e) {
            res.setCode(-1);
            res.setData("无效id");
            return res;
        }
        list.add(userMapper.getUserByUserId(obUser_id));

        res.setCode(0);
        res.setData(list);
        return res;
    }


}
