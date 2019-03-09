package com.lry.xxs.service;

import com.lry.xxs.mapper.ParentMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private StudentService studentService;

    public void add(PageData pd) {
        if (StringUtils.isNotBlank(pd.getString("student_class")) && StringUtils.isNotBlank(pd.getString("student_grade"))){
            pd.put("student_status",1);
            PageData temppd = new PageData();
            temppd.put("class_grade",pd.getString("student_grade"));
            temppd.put("class_class",pd.getString("student_class"));
            studentService.select(temppd);
        }
        parentMapper.add(pd);
    }

    public void deleteById(String id) {
        parentMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        parentMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return parentMapper.select(pd);
    }
}
