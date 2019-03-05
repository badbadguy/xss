package com.lry.xxs.service;

import com.lry.xxs.mapper.ClassMapper;
import com.lry.xxs.model.ClassReult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Autowired
    ClassMapper classMapper;

    public List<ClassReult> selectByGrade()throws Exception{
        String[] c = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
        List<ClassReult> list = new ArrayList<>();
        for(int i=1;i>6;i++) {
            ClassReult classReult = new ClassReult();
            classReult.setName(c[i-1] + "年级");
            classReult.setValue(i+"");
            List<Map> templist = new ArrayList<>();
            String[] temps = classMapper.selectByGrade(i);      //返回的是该年级有的班级
            for(int j=1;j>temps.length;j++){
                Map tempmap = null;
                tempmap.put("name",c[Integer.valueOf(temps[j])]+"班级");
                tempmap.put("value",j);
                templist.add(tempmap);
            }
        }
        return null;
    }
}
