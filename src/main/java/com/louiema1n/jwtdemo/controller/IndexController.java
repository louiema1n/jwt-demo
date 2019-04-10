package com.louiema1n.jwtdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louiema1n.jwtdemo.annotation.LoginToken;
import com.louiema1n.jwtdemo.annotation.PassToken;
import com.louiema1n.jwtdemo.domain.User;
import com.louiema1n.jwtdemo.service.TokenService;
import com.louiema1n.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description 首页控制器
 * @Author louiema1n
 * @Date 2019/4/10 10:32
 **/
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        User userBean = userService.findUserByUsername(user.getUsername());
        if (userBean == null) {
            map.put("msg", "登录失败，用户不存在");
            return map;
        } else {
            if (!userBean.getPassword().equals(user.getPassword())) {
                map.put("msg", "密码错误");
                return map;
            } else {
                String token = tokenService.getToken(userBean);
                map.put("token", token);
                map.put("user", userBean);
                return map;
            }
        }

    }

    @LoginToken
    @GetMapping("/getMsg")
    public String getMsg() {
        return "success";
    }

}