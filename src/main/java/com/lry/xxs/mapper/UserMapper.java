package com.lry.xxs.mapper;

import com.lry.xxs.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /*@Insert("INSERT INTO user(user_id,user_name,user_nickname,user_image,user_password,user_type,creattime,updatetime,lastLogintime) " +
            "VALUES (#(user_id),#(user_name),#(user_nickname),#(user_image),#(user_password)，#(user_type)，#(creattime)，#(updatetime)，#(lastLogintime))")
    void add(User user);

    @Delete("DELETE FROM user WHERE user_id=#{1}")
    void deleteById(String Id);

    @Update("")
    void updateById(User user);

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User selectById(Integer id);*/

    User selectById(Integer id);
}