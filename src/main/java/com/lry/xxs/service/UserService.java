package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import com.lry.xxs.utils.MD5;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    MD5 md5 = new MD5();

    public void add(User user)throws Exception{
        user.setUser_id(UuidUtil.get32UUID());
        user.setCreattime(new Date());
        user.setUpdatetime(new Date());
        user.setUser_password(md5.EncoderByMd5(user.getUser_password().toString().trim()));
        userMapper.add(user);
    }

    public void deleteById(String id){
        userMapper.deleteById(id);
    }

    public void updateById(User user){
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    public User selectById(String id){
        return userMapper.selectById(id);
    }

    public Integer checkPw(String name, String password)throws Exception{
        String temppw = userMapper.checkPw(name);
        if(StringUtils.isNotBlank(temppw)) {
            if (md5.checkpassword(password, temppw))
                return 666;
            else
                return 1;
        }else {
            return 2;
        }
    }

    public void changePw(String name, String password)throws Exception{
        userMapper.changePw(name, md5.EncoderByMd5(password));
    }

    public PageData login(String name, Integer type){
        PageData pd = new PageData();
        pd.put("user_name",name);
        pd.putAll(userMapper.select(pd));
        if((Integer)pd.get("user_type") == type){
            return pd;
        }else {
            pd.clear();
            pd.put("error","用户类型错误");
            return pd;
        }
    }
}
