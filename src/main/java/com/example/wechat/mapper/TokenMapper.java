package com.example.wechat.mapper;

import com.example.wechat.entity.Token;
import com.example.wechat.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TokenMapper {

    @Insert("INSERT INTO chat_token (token, user_id) VALUES (#{token}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Token token);

    @Select("SELECT * FROM chat_token where user_id = #{userId}")
    Token select(long userId);

    @Select("SELECT * FROM chat_token where token = #{token}")
    Token selectUserToken(String token);

    @Update("UPDATE chat_token SET token = #{token} WHERE user_id = #{userId}")
    void update(Token token);
}
