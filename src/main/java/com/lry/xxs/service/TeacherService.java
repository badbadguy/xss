package com.lry.xxs.service;

import com.lry.xxs.mapper.ClassMapper;
import com.lry.xxs.mapper.TeacherMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassMapper classMapper;

    public void add(PageData pd) {
        if (StringUtils.isNotBlank(pd.getString("address")))
            pd.put("teacher_address", pd.getString("address"));
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

    public List<PageData> checkAllT() throws Exception {
        List<PageData> returnList = teacherMapper.checkAllT();
        String[] c = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        for (PageData pd : returnList) {
            if ((Integer) pd.get("teacher_ishead") == 0) {
                String ai = "";
                PageData tempPd = new PageData();
                tempPd.put("class_id", pd.getString("teacher_headClass"));
                tempPd = classMapper.select(tempPd).get(0);
                for (int j = 0; j < c.length; j++) {
                    if ((Integer) tempPd.get("class_class") == j) {
                        ai = c[j - 1] + "年";
                        break;
                    }
                }
                for (int j = 0; j < c.length; j++) {
                    if ((Integer) tempPd.get("class_grade") == j) {
                        ai += c[j - 1] + "班";
                        break;
                    }
                }
                pd.put("teacher_headClass", ai);
            }else {
                pd.put("teacher_headClass", "暂无负责班主任班级");
            }

            String tempName = pd.getString("user_name");
            pd.put("user_name", new String(Base64.decodeBase64(tempName), "UTF-8"));
            String[] tempClass = pd.getString("teacher_class").split(",");
            String returnClass = "";
            if (tempClass.length > 0) {
                for (int i = 0; i < tempClass.length; i++) {
                    PageData tempPD = new PageData();
                    tempPD.put("class_id", tempClass[i]);
                    tempPD = classMapper.select(tempPD).get(0);
                    for (int j = 0; j < c.length; j++) {
                        if ((Integer) tempPD.get("class_class") == j) {
                            returnClass += c[j - 1] + "年";
                            break;
                        }
                    }
                    for (int j = 0; j < c.length; j++) {
                        if ((Integer) tempPD.get("class_grade") == j) {
                            returnClass += c[j - 1] + "班,";
                            break;
                        }
                    }
                }
            }else {
                returnClass = "暂无负责班级";
            }
            pd.put("class", returnClass);
        }
        return returnList;
    }
}
