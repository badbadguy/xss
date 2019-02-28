package com.lry.xxs.mapper;

import com.lry.xxs.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User selectById(Integer id);
}