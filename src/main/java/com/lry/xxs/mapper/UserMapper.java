package com.lry.xxs.mapper;

import com.lry.xxs.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    void add(User user);

    void deleteById(String Id);

    void updateById(User user);

    User selectById(String id);

    String checkPw(String name);
}