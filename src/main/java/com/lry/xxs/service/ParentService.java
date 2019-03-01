package com.lry.xxs.service;

import com.lry.xxs.mapper.ParentMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    @Autowired
    private ParentMapper parentMapper;

    public void add(PageData pd) {
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
