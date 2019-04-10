package com.louiema1n.jwtdemo.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @Author louiema1n
 * @Date 2019/4/10 11:01
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handleException(Exception e) {
        String msg = e.getMessage();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(msg)) {
            msg = "服务器异常";
        }
        map.put("msg", msg);
        map.put("exception", e.getClass());
        map.put("lines", e.getStackTrace()[0].getLineNumber());
        return map;
    }
}