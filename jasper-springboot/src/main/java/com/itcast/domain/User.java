package com.itcast.domain;


public class User {
    private String id;
    private String username;
    private String mobile;
    private String dept;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public User() {
    }

    public User(String id, String username, String mobile, String dept) {
        this.id = id;
        this.username = username;
        this.mobile = mobile;
        this.dept = dept;
    }
}
