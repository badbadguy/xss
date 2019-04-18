package com.lry.xxs.mapper;

import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveMapper {

    void add(PageData pd);

    void updateById(PageData pd);

    List<PageData> select(PageData pd);

    List<PageData> select1(PageData pd);
}
