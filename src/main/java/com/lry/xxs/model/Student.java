package com.lry.xxs.model;

public class Student {
    private String user_id;                 //用户id
    private String student_address;         //学生住址
    private String student_class;           //学生班级
    private String student_remark;          //备注

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_remark() {
        return student_remark;
    }

    public void setStudent_remark(String student_remark) {
        this.student_remark = student_remark;
    }
}
