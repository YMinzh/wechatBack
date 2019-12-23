package com.example.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.wechat.entity.Measure;
import com.example.wechat.entity.Response;
import com.example.wechat.entity.Token;
import com.example.wechat.mapper.MeasureMapper;
import com.example.wechat.mapper.TokenMapper;
import com.example.wechat.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MeasureController {

    @Autowired
    MeasureMapper measureMapper;
    @Autowired
    TokenMapper tokenMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("measure/send")
    public Response addMeasure(HttpServletRequest request, @RequestBody Measure measure){
        Response res = new Response();
        String tokenValue = request.getParameter("token");
        Token token = tokenMapper.selectUserToken(tokenValue);
        //验证token是否存在
        if (token==null){
            res.setCode(-1);
            res.setData("无效的token");
            return res;
        }
        //验证目标id是否存在
        long targetId = measure.getTargetId();
        if (userMapper.getUserByUserId(targetId)==null){
            res.setCode(-1);
            res.setData("目标id不存在");
            return res;
        }

        long userId = token.getUserId();
        measure.setSendId(userId);
        measure.setStatus(0);

        measureMapper.addMeasure(measure);
        res.setCode(0);
        res.setData("发送成功");
        return res;
    }

    @RequestMapping("/measure/unread")
    public Response unreadMeasure(HttpServletRequest request){
        Response res = new Response();
        String tokenValue = request.getParameter("token");
        if (tokenValue==null){
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        Token token = tokenMapper.selectUserToken(tokenValue);

        if (token==null){
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        long targetId = token.getUserId();

        List<JSONObject> list= measureMapper.getMeasureList(targetId);
        res.setData(list);
        res.setCode(0);
        return res;
    }

    @Transactional
    @RequestMapping("measure/details")
    public Response getDetails(HttpServletRequest request,@RequestBody Measure measure){
        Response res = new Response();
        String tokenValue = request.getParameter("token");
        if (tokenValue==null){
            res.setCode(-1);
            res.setData("没有token");
            return res;
        }

        Token token = tokenMapper.selectUserToken(tokenValue);

        if (token==null){
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        long targetId = token.getUserId();

        measure.setTargetId(targetId);

        List<Measure> list = null;
        try {
            list = measureMapper.getDetails(measure);
            if (list.size()!=0){
                measureMapper.updateStatus(measure);
            }
        }catch (Exception e){
            System.out.println("sql出错");
            res.setCode(-1);
            res.setData("sql出错");
            return res;
        }


        res.setCode(0);
        res.setData(list);
        return res;
    }

    @RequestMapping("/measure/page")
    public Response getPage(HttpServletRequest request){
        Response res = new Response();
        String tokenValue = request.getParameter("token");
        if (tokenValue==null){
            res.setCode(-1);
            res.setData("没有token");
            return res;
        }

        Token token = tokenMapper.selectUserToken(tokenValue);

        if (token==null){
            res.setCode(-1);
            res.setData("无效token");
            return res;
        }

        long targetId = token.getUserId();

        long sendId = 0;
        try {
            sendId = Long.parseLong(request.getParameter("sendId"));
        } catch (NumberFormatException e) {
            res.setCode(-1);
            res.setData("无效id");
            return res;
        }

        int num = 0;
        try {
            num = Integer.parseInt(request.getParameter("page"));
        }catch (NumberFormatException e){
            res.setCode(-1);
            res.setData("无效的页数");
            return res;
        }


        PageHelper.startPage(num,10);
        List list = measureMapper.getAllByPage(sendId,targetId);
        PageInfo pageInfo = new PageInfo(list);


        res.setData(pageInfo);

        return res;
    }
}
