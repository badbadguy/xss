package com.lry.xxs.mapper;

import com.lry.xxs.model.Student;
import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {

    void add(PageData pd);

    void deleteById(String Id);

    void updateById(PageData pd);

    PageData select(PageData pd);
}
