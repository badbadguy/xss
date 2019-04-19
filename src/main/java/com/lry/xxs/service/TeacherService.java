package com.lry.xxs.service;

import com.lry.xxs.mapper.ClassMapper;
import com.lry.xxs.mapper.TeacherMapper;
import com.lry.xxs.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

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
            switch (pd.getString("teacher_subject")) {
                case "33018ef1b3b74a18b6d9f94bff995d79":
                    pd.put("teacher_subject", "语文");
                    break;
                case "79bed2b0e57c4f7f8e71b9817f03e3b9":
                    pd.put("teacher_subject", "英语");
                    break;
                case "cd84a79d6ee04e4d9630731b25b589d0":
                    pd.put("teacher_subject", "数学");
                    break;
                default:
                    pd.put("teacher_subject", "未设置学科");
                    break;
            }
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
            } else {
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
            } else {
                returnClass = "暂无负责班级";
            }
            pd.put("class", returnClass);
        }
        return returnList;
    }

    //返回班级负责老师
    public List<PageData> classTeacher(PageData pd) throws Exception{
        List<PageData> tempList = userMapper.select(pd);
        List<PageData> list = new ArrayList<>();
        if((Integer) tempList.get(0).get("user_type")==3) {
            list = teacherMapper.classTeacher(pd.getString("user_id"));
        }else {
            list = teacherMapper.classTeacher1(pd.getString("user_id"));
        }
        List<PageData> returnList = new ArrayList<>();
        Integer i = 0;
        for(PageData tempPD : list){
            PageData returnPD = new PageData();
            tempPD.put("user_name",new String(Base64.decodeBase64(tempPD.getString("user_name")), "UTF-8"));
            switch (tempPD.getString("teacher_subject")){
                case "33018ef1b3b74a18b6d9f94bff995d79":
                    tempPD.put("subTitle","语文");
                case "cd84a79d6ee04e4d9630731b25b589d0":
                    tempPD.put("subTitle","数学");
                case "79bed2b0e57c4f7f8e71b9817f03e3b9":
                    tempPD.put("subTitle","英语");
            }
            returnPD.put("key",i);
            returnPD.put("tid",tempPD.getString("tid"));
            returnPD.put("value",tempPD.getString("user_name"));
            returnPD.put("subTitle",tempPD.getString("subTitle"));
            returnList.add(returnPD);
            i++;
        }
        return returnList;
    }
}
