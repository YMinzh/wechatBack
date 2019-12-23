package com.example.wechat.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.wechat.entity.Friend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FriendMapper {

    @Select("SELECT * FROM chat_friend cf, chat_user cu where cf.user_id = #{user_id} and cu.id = cf.friend_id")
    List<JSONObject> select(long user_id);
}
