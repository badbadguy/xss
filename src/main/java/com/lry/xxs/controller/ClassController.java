package com.lry.xxs.controller;

import com.lry.xxs.model.ClassReult;
import com.lry.xxs.service.ClassService;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/class")
@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    private ResultJson resultJson = null;

    @ResponseBody
    @RequestMapping("/checkclass2")
    public List<ClassReult> checkclass2(){
        classService.selectByGrade()
    }
}
