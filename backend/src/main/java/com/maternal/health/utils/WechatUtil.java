package com.maternal.health.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.maternal.health.config.WechatMiniAppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信工具类
 * 功能：提供微信API调用相关方法
 */
@Slf4j
@Component
public class WechatUtil {

    @Autowired
    private WechatMiniAppConfig wechatConfig;

    /**
     * 微信登录凭证校验（code2Session）
     * 功能：通过wx.login()获取的code，换取用户openid和session_key
     *
     * @param code 微信登录凭证code
     * @return 包含openid、session_key等信息的对象，失败返回null
     */
    public WxSessionInfo code2Session(String code) {
        try {
            // 构建请求URL
            String url = String.format(
                "%s/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatConfig.getApiUrl(),
                wechatConfig.getAppid(),
                wechatConfig.getSecret(),
                code
            );

            log.info("调用微信code2Session接口：code={}", code);

            // 发送HTTP GET请求
            String responseStr = HttpUtil.get(url);
            log.info("微信code2Session响应：{}", responseStr);

            // 解析响应
            JSONObject response = JSONUtil.parseObj(responseStr);

            // 检查是否有错误
            if (response.containsKey("errcode")) {
                Integer errcode = response.getInt("errcode");
                if (errcode != 0) {
                    String errmsg = response.getStr("errmsg");
                    log.error("微信code2Session失败：errcode={}, errmsg={}", errcode, errmsg);
                    return null;
                }
            }

            // 构造返回对象
            WxSessionInfo sessionInfo = new WxSessionInfo();
            sessionInfo.setOpenid(response.getStr("openid"));
            sessionInfo.setSessionKey(response.getStr("session_key"));
            sessionInfo.setUnionid(response.getStr("unionid"));

            log.info("微信code2Session成功：openid={}", sessionInfo.getOpenid());
            return sessionInfo;

        } catch (Exception e) {
            log.error("调用微信code2Session接口异常", e);
            return null;
        }
    }

    /**
     * 微信会话信息
     */
    public static class WxSessionInfo {
        /**
         * 用户唯一标识
         */
        private String openid;

        /**
         * 会话密钥
         */
        private String sessionKey;

        /**
         * 用户在开放平台的唯一标识符（如果有）
         */
        private String unionid;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }
    }
}
