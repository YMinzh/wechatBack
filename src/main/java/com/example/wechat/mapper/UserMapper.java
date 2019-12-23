package com.example.wechat.mapper;

import com.example.wechat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from chat_user where username = #{username}")
    User getUserByUsername(User user);

    @Select("select * from chat_user where id = #{id}")
    User getUserByUserId(long id);

}
