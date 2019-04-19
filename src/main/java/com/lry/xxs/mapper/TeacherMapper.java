package com.lry.xxs.mapper;

import com.lry.xxs.model.Teacher;
import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper {

    void add(PageData pd);

    void deleteById(String Id);

    void updateById(PageData pd);

    void cancleH(String user_id);

    List<PageData> select(PageData pd);

    List<PageData> selectCheckT(PageData pd);

    List<PageData> checkAllT();

    List<PageData> classTeacher(String user_id);
}
