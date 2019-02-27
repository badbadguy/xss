package com.lry.xxs.mapper;

import com.lry.xxs.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    //新增
    public void save(PageData pd)throws Exception;

    //删除
    public void delete(PageData pd)throws Exception;

    //修改
    public void edit(PageData pd)throws Exception;

    //列表
    public List<PageData> datalistPage(PageData pd)throws Exception;

    //列表全部
    public List<PageData> listAll()throws Exception;

    //根据id查找
    public List<PageData> findById(PageData pd)throws Exception;
}