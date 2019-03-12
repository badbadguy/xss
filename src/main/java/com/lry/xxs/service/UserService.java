package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import com.lry.xxs.utils.FastDFS;
import com.lry.xxs.utils.MD5;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
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
            //微信头像图片获取
            URL tempurl = new URL(pd.getString("user_image").substring(0,pd.getString("user_image").length()-3) + "0");
            InputStream is = tempurl.openStream();
            MultipartFile multipartFile = new MockMultipartFile("temp.jpg", "temp.jpg", "", is);
            pd.put("user_image",new FastDFS().saveFile(multipartFile));
            if (StringUtils.isNotBlank(pd.getString("user_nickname")))
                //将微信名进行编码转换  防止用户账号中昵称特殊符号的报错
                pd.put("user_nickname", Base64.encodeBase64String(pd.getString("user_nickname").getBytes("UTF-8")));
                //取出方法
                //new String(Base64.decodeBase64(user_nickname), "UTF-8");
        }
        pd.put("user_name", Base64.encodeBase64String(pd.getString("user_name").getBytes("UTF-8")));
        if(StringUtils.isBlank(pd.getString("user_id")))
            pd.put("user_id", UuidUtil.get32UUID());
        pd.put("creattime", new Date());
        pd.put("updatetime", new Date());
        pd.put("lastLogintime", new Date());
        if (StringUtils.isNotBlank(pd.getString("user_password")))
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

    public List<PageData> select(PageData pd)throws Exception {
        List<PageData> tempList = userMapper.select(pd);
        for(PageData temppd : tempList){
            temppd.put("user_name",new String(Base64.decodeBase64(temppd.getString("user_name")), "UTF-8"));
            temppd.put("user_nickname",new String(Base64.decodeBase64(temppd.getString("user_nickname")), "UTF-8"));
        }
        return tempList;
    }

    public Integer checkPw(String user_id, String password) throws Exception {
        String temppw = userMapper.checkPw(user_id);
        if (StringUtils.isNotBlank(temppw)) {
            if (md5.checkpassword(password, temppw))
                return 666;
            else
                return 1;
        } else {
            return 2;
        }
    }

    public void changePw(String user_id, String password) throws Exception {
        userMapper.changePw(user_id, md5.EncoderByMd5(password));
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

    public void updateLastLoginTime(User user) {
        userMapper.updateById(user);
    }
}
