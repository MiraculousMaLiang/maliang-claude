package com.maternal.health.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 * 功能：配置Sa-Token拦截器,设置哪些接口需要登录认证
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token拦截器
     * 功能：配置需要登录认证的接口路径和排除的接口路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token拦截器，校验规则为StpUtil.checkLogin()登录校验
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        // 排除不需要登录的接口
                        "/auth/**",           // 登录接口  // 注册接口  // 微信登录接口
                        "/api/test/**",              // 测试接口
                        "/error",                    // 错误页面
                        "/favicon.ico"               // 网站图标
                );
    }
}
