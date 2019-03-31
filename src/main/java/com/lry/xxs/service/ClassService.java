package com.lry.xxs.service;

import com.lry.xxs.mapper.ClassMapper;
import com.lry.xxs.mapper.StudentMapper;
import com.lry.xxs.mapper.TeacherMapper;
import com.lry.xxs.model.ClassReult;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;

    //查询数据库中的班级
    public List<ClassReult> selectByGrade() throws Exception {
        String[] c = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        List<ClassReult> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            ClassReult classReult = new ClassReult();
            classReult.setName(c[i - 1] + "年级");
            classReult.setValue(i + "");
            List<Map> templist = new ArrayList<>();
            String[] temps = classMapper.selectByGrade(i);      //返回的是该年级有的班级
            for (int j = 0; j < temps.length; j++) {
                Map tempmap = new HashMap();
                tempmap.put("name", c[Integer.valueOf(temps[j]) - 1] + "班");
                tempmap.put("value", j + 1);
                templist.add(tempmap);
            }
            classReult.setChildren(templist);
            list.add(classReult);
        }
        return list;
    }

    //根据年级班级  查询id
    public String selectClassID(PageData pd) {
        return classMapper.selectClassID(pd);
    }

    //管理员设置教师负责班级
    public void chooseClass(PageData pd) {
        teacherMapper.updateById(pd);
    }

    //查询数据库中的班级
    public List<Map> selectByTeacher(String teacher_class) throws Exception {
        String[] tempClass = teacher_class.split(",");
        String[] c = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        List<Map> templist = new ArrayList<>();
        for(String temp : tempClass){
            if(StringUtils.isNotBlank(temp)){
                Map tempmap = new HashMap();
                PageData temppd = new PageData();
                temppd.put("class_id",temp);
                temppd = classMapper.select(temppd).get(0);
                for(int i = 1; i<=10; i++){
                    if((Integer)temppd.get("class_grade") == i){
                        tempmap.put("value",c[i-1] + "年级");
                    }
                }
                for(int i = 1; i<=10; i++){
                    if((Integer)temppd.get("class_class") == i){
                        tempmap.put("value",tempmap.get("value") + c[i-1] + "班");
                    }
                }
                tempmap.put("key",temppd.getString("class_id"));
                templist.add(tempmap);
            }
        }
        return templist;
    }

    //查询班主任负责班级的申请
    public List<Map> checkstudent(PageData pd)throws Exception{
        List<Map> tempList = new ArrayList<>();
        List<PageData> list = teacherMapper.select(pd);
        pd = list.get(0);
        if(pd.get("teacher_ishead")==null || (Integer)pd.get("teacher_ishead") == 1){
            return new ArrayList<>();
        }else{
            list = studentMapper.select1(pd.getString("teacher_headClass"));
            for(PageData PD : list){
                Map tempMap = new HashMap();
                String user_name = new String(Base64.decodeBase64(PD.getString("user_name")), "UTF-8");
                tempMap.put("user_name",user_name);
                tempMap.put("user_id",PD.getString("user_id"));
                tempMap.put("height",80);
                tempMap.put("color","#39CCC5");
                tempMap.put("text",PD.getString("student_address"));
                tempMap.put("switcher","#off");
                tempList.add(tempMap);
            }
            return tempList;
        }
    }
}
