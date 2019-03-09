package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import com.lry.xxs.utils.CommonInfo;
import com.lry.xxs.utils.MD5;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ParentService parentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    MD5 md5 = new MD5();

    public void add(PageData pd) throws Exception {
        //获取注册用户的头像昵称
        if(StringUtils.isNotBlank(pd.getString("user_image"))){
            String user_image = pd.getString("user_image");
            user_image = user_image.substring(0,user_image.length()-3) + "0";
        }
        if(StringUtils.isBlank(pd.getString("user_id")))
            pd.put("user_id", UuidUtil.get32UUID());
        pd.put("creattime", new Date());
        pd.put("updatetime", new Date());
        pd.put("user_password", md5.EncoderByMd5(pd.getString("user_password").toString().trim()));
        switch (Integer.valueOf(pd.getString("user_type"))) {
            case 0:
                System.out.println("新增超级管理员");
                break;
            case 1:
                System.out.println("新增普通管理员");
                break;
            case 2:
                teacherService.add(pd);
                break;
            case 3:
                studentService.add(pd);
                break;
            case 4:
                parentService.add(pd);
                break;
        }
        userMapper.add(pd);
    }

    public boolean deleteById(String id) {
        PageData pd = new PageData();
        pd.put("user_id", id);
        List<PageData> templist = userMapper.select(pd);
        if (!templist.isEmpty() && templist.size() >= 1)
            pd.putAll(templist.get(0));
        else
            return false;
        if (StringUtils.isBlank(pd.get("user_type").toString()))
            return false;
        else {
            switch ((Integer) pd.get("user_type")) {
                case 0:
                    System.out.println("删除超级管理员");
                    break;
                case 1:
                    System.out.println("删除普通管理员");
                    break;
                case 2:
                    teacherService.deleteById(id);
                    break;
                case 3:
                    studentService.deleteById(id);
                    break;
                case 4:
                    parentService.deleteById(id);
                    break;
            }
            userMapper.deleteById(id);
            return true;
        }
    }

    public void updateById(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    public User selectById(String id) {
        return userMapper.selectById(id);
    }

    public List<PageData> select(PageData pd) {
        return userMapper.select(pd);
    }

    public Integer checkPw(String name, String password) throws Exception {
        String temppw = userMapper.checkPw(name);
        if (StringUtils.isNotBlank(temppw)) {
            if (md5.checkpassword(password, temppw))
                return 666;
            else
                return 1;
        } else {
            return 2;
        }
    }

    public void changePw(String name, String password) throws Exception {
        userMapper.changePw(name, md5.EncoderByMd5(password));
    }

    public PageData login(String name, Integer type) {
        PageData pd = new PageData();
        pd.put("user_name", name);
        List<PageData> templist = userMapper.select(pd);
        if (!templist.isEmpty() && templist.size() >= 1)
            pd.putAll(templist.get(0));
        if ((Integer) pd.get("user_type") == type) {
            return pd;
        } else {
            pd.clear();
            pd.put("error", "用户类型错误");
            return pd;
        }
    }
}
