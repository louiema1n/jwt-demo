package com.louiema1n.jwtdemo.service;

import com.louiema1n.jwtdemo.domain.User;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description 用户相关服务
 * @Author louiema1n
 * @Date 2019/4/10 10:16
 **/
@Service
public class UserService {

    public User findUserById(String id) {
        User user = new User();
        if ("1".equals(id)) {
            user.setId("1");
            user.setUsername("admin");
            user.setPassword("123");
        }
        return user;
    }

    public User findUserByUsername(String username) {
        User user = new User();
        if ("admin".equals(username)) {
            user.setId("1");
            user.setUsername("admin");
            user.setPassword("123");
        }
        return user;
    }
}