package com.lry.xxs.model;

public class Parent {
    private String user_id;            //用户id
    private String children_id;        //所绑定的学生id
    private String parent_phone;       //家长电话
    private Integer parent_sex;        //家长类别(0:妈妈 1:爸爸)
    private String parent_remark;      //备注

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getChildren_id() {
        return children_id;
    }

    public void setChildren_id(String children_id) {
        this.children_id = children_id;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public Integer getParent_sex() {
        return parent_sex;
    }

    public void setParent_sex(Integer parent_sex) {
        this.parent_sex = parent_sex;
    }

    public String getParent_remark() {
        return parent_remark;
    }

    public void setParent_remark(String parent_remark) {
        this.parent_remark = parent_remark;
    }
}
