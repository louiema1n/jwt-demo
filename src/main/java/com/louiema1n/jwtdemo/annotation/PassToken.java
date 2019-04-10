package com.louiema1n.jwtdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 跳过验证注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})     // 目标 接口、类、枚举、注解、方法
@Retention(RetentionPolicy.RUNTIME)                 // 保留位置 运行时
public @interface PassToken {
    boolean required() default true;
}
