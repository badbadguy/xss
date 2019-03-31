package com.lry.xxs.mapper;

import com.lry.xxs.model.Student;
import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {

    void add(PageData pd);

    void deleteById(String Id);

    void updateById(PageData pd);

    List<PageData> select(PageData pd);

    //返回学生信息（用于教师通过班级绑定申请）
    List<PageData> select1(String student_class);
}
