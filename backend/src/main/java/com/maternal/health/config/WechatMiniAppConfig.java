package com.maternal.health.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置类
 * 功能：从配置文件读取微信小程序相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMiniAppConfig {

    /**
     * 小程序AppID
     */
    private String appid;

    /**
     * 小程序AppSecret
     */
    private String secret;

    /**
     * 微信API地址
     */
    private String apiUrl;
}
