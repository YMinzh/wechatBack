package com.example.wechat.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.wechat.entity.Measure;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface MeasureMapper {

    @Insert("INSERT INTO chat_measure (send_id, target_id, content, status) VALUES (#{send_id}, #{target_id}, #{content}, #{status} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addMeasure(Measure measure);

    @Select("SELECT * from chat_user cu right join (SELECT send_id, count(*) as count, MAX(created_at) as latest_time FROM chat_measure WHERE status = 0 and target_id = #{target_id} GROUP BY send_id ORDER BY latest_time) c2 on cu.id = c2.send_id;;")
    List<JSONObject> getMeasureList(long target_id);

    @Select("SELECT * FROM chat_measure WHERE target_id = #{target_id} and send_id = #{send_id} and status = 0 ORDER BY created_at ")
    List<Measure>getDetails(Measure measure);

    @Update("UPDATE chat_measure SET status = 1 WHERE target_id = #{target_id} and send_id = #{send_id} and status = 0 ")
    void updateStatus(Measure measure);

    @Select("SELECT * FROM chat_measure where (send_id = #{from} and target_id = #{to}) or (send_id = #{to} and target_id = #{from}) ORDER BY created_at DESC")
    List<JSONObject> getAllByPage(long from, long to);

//    @Select("SELECT * FROM measure ORDER BY created_at LIMIT #{page.offset}, #{page.limit}")
//    List<Measure> getAllByPage2(@Param("page") Map<String, Object> page);
}
