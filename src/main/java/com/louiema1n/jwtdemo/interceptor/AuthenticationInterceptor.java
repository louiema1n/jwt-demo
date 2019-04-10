package com.louiema1n.jwtdemo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.louiema1n.jwtdemo.annotation.LoginToken;
import com.louiema1n.jwtdemo.annotation.PassToken;
import com.louiema1n.jwtdemo.domain.User;
import com.louiema1n.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName AuthenticationInterceptor
 * @Description 验证相关拦截器
 * @Author louiema1n
 * @Date 2019/4/10 9:56
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    /**
     * 预处理
     * @param request
     * @param response
     * @param handler
     * @return true - 继续执行；false - 中断执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从Header中取出token
        String token = request.getHeader("token");
        // 2. 判断是否映射到方法
        if (!(handler instanceof HandlerMethod)) {
            // 2.1. 不是，直接跳过
            return true;
        }
        // 2.2. 是，获取方法
        Method method = ((HandlerMethod) handler).getMethod();
        // 3. 校验PassToken
        if (method.isAnnotationPresent(PassToken.class)) {
            // 有，判断注解值
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                // true，表示跳过
                return true;
            }
        }
        // 4. 校验LoginToken
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                // 需要登录验证
                if (StringUtils.isEmpty(token)) {
                    throw new RuntimeException("未找到有效token，请重新登录");
                }
                // 获取userId
                String userId;
                try {
                    List<String> audiences = JWT.decode(token).getAudience();
                    userId = audiences
                            .get(0);
                } catch (JWTDecodeException e) {
                    throw new RuntimeException("无效token，请重新登录");
                }
                // 验证用户
                User user = userService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("无效token，请重新登录");
                }
                return true;
            }
        }
        return true;
    }

    /**
     * 后处理
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 处理完成回调
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}