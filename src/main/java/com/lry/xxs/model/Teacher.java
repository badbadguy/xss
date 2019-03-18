package com.lry.xxs.model;

public class Teacher {
    private String user_id;             //用户id
    private String teacher_phone;       //教师电话
    private String teacher_address;     //教师地址
    private String teacher_class;       //负责的班级
    private String teacher_subject;     //负责的学科
    private String teacher_remark;      //备注
    private Integer teacher_ishead;     //是否班主任(0:是 1:不是)
    private String teacher_headClass;   //班主任班级

    public String getTeacher_headClass() {
        return teacher_headClass;
    }

    public void setTeacher_headClass(String teacher_headClass) {
        this.teacher_headClass = teacher_headClass;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTeacher_phone() {
        return teacher_phone;
    }

    public void setTeacher_phone(String teacher_phone) {
        this.teacher_phone = teacher_phone;
    }

    public String getTeacher_address() {
        return teacher_address;
    }

    public void setTeacher_address(String teacher_address) {
        this.teacher_address = teacher_address;
    }

    public String getTeacher_class() {
        return teacher_class;
    }

    public void setTeacher_class(String teacher_class) {
        this.teacher_class = teacher_class;
    }

    public String getTeacher_subject() {
        return teacher_subject;
    }

    public void setTeacher_subject(String teacher_subject) {
        this.teacher_subject = teacher_subject;
    }

    public String getTeacher_remark() {
        return teacher_remark;
    }

    public void setTeacher_remark(String teacher_remark) {
        this.teacher_remark = teacher_remark;
    }

    public Integer getTeacher_ishead() {
        return teacher_ishead;
    }

    public void setTeacher_ishead(Integer teacher_ishead) {
        this.teacher_ishead = teacher_ishead;
    }
}
