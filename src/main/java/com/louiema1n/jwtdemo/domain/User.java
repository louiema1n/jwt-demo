package com.louiema1n.jwtdemo.domain;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author louiema1n
 * @Date 2019/4/10 9:43
 **/
public class User {
    String id;
    String username;
    String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}