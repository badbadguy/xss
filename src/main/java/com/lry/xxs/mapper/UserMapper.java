package com.lry.xxs.mapper;

import com.lry.xxs.model.User;
import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    void add(PageData pd);

    void deleteById(String Id);

    void updateById(User user);

    User selectById(String id);

    String checkPw(String name);

    void changePw(String name, String password);

    String selectIdByName(String name);

    List<PageData> select(PageData pd);
}