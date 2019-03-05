package com.lry.xxs.model;

import java.util.List;
import java.util.Map;

public class ClassReult {
    private String name;
    private String value;
    private List<Map> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Map> getChildren() {
        return children;
    }

    public void setChildren(List<Map> children) {
        this.children = children;
    }
}