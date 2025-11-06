package com.maternal.health;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 孕产妇全周期健康管理系统 - 主启动类
 * 功能：Spring Boot应用程序入口
 */
@SpringBootApplication
@MapperScan("com.maternal.health.mapper")
public class MaternalHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaternalHealthApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("孕产妇健康管理系统启动成功！");
        System.out.println("API地址: http://localhost:8080/api");
        System.out.println("========================================\n");
    }
}
