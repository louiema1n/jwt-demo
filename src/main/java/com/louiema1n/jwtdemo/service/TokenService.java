package com.louiema1n.jwtdemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.louiema1n.jwtdemo.domain.User;
import org.springframework.stereotype.Service;

/**
 * @ClassName TokenService
 * @Description Token相关服务
 * @Author louiema1n
 * @Date 2019/4/10 9:49
 **/
@Service
public class TokenService {

    /**
     * 根据用户信息获取token
     * @param user
     * @return
     */
    public String getToken(User user) {
        return JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }
}