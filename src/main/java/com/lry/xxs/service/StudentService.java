package com.lry.xxs.service;

import com.lry.xxs.mapper.StudentMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassService classService;

    public void add(PageData pd) {
        if (StringUtils.isNotBlank(pd.getString("student_class")) && StringUtils.isNotBlank(pd.getString("student_grade"))){
            pd.put("student_status",1);
            PageData temppd = new PageData();
            temppd.put("class_grade",pd.getString("student_grade"));
            temppd.put("class_class",pd.getString("student_class"));
            pd.put("student_class",classService.selectClassID(temppd));
        }
        if (StringUtils.isNotBlank(pd.getString("address")))
            pd.put("student_address",pd.getString("address"));
        studentMapper.add(pd);
    }

    public void deleteById(String id) {
        studentMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        studentMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return studentMapper.select(pd);
    }
}
