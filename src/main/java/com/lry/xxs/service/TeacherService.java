package com.lry.xxs.service;

import com.lry.xxs.mapper.TeacherMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public void add(PageData pd) {
        if (StringUtils.isNotBlank(pd.getString("address")))
            pd.put("teacher_address",pd.getString("address"));
        teacherMapper.add(pd);
    }

    public void deleteById(String id) {
        teacherMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        teacherMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return teacherMapper.select(pd);
    }
}
