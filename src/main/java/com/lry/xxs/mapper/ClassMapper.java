package com.lry.xxs.mapper;

import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassMapper {

    void add(PageData pd);

    void deleteById(String Id);

    void updateById(PageData pd);

    List<PageData> select(PageData pd);

    String[] selectByGrade(Integer grade);

    String selectClassID(PageData pd);
}
