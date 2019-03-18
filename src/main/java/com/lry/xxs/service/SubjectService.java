package com.lry.xxs.service;

import com.lry.xxs.mapper.SubjectMapper;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    public void add(PageData pd) {
        pd.put("subject_id", UuidUtil.get32UUID());
        subjectMapper.add(pd);
    }

    public void delete(String id) {
        subjectMapper.deleteById(id);
    }

    public void update(PageData pd) {
        subjectMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return subjectMapper.select(pd);
    }
}
