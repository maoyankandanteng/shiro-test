package com.itcast.domain;

public class UserCount {
    private String dept;
    private Long count;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public UserCount() {
    }

    public UserCount(String dept, Long count) {
        this.dept = dept;
        this.count = count;
    }
}
