package com.maternal.health.controller;

import com.maternal.health.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 * 功能：提供系统健康检查和框架测试接口
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 健康检查接口
     * 功能：测试系统是否正常运行
     */
    @GetMapping("/health")
    public R<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "ok");
        data.put("message", "孕产妇健康管理系统运行正常");
        data.put("timestamp", System.currentTimeMillis());
        return R.ok(data);
    }

    /**
     * 测试接口
     * 功能：测试统一返回格式
     */
    @GetMapping("/hello")
    public R<Void> hello() {
        return R.ok("Hello, Maternal Health System!");
    }
}
