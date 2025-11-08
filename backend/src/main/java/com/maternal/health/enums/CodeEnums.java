package com.maternal.health.enums;

/**
 * 响应状态码枚举
 * 功能：定义API响应的状态码常量
 */
public class CodeEnums {

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "200";

    /**
     * 错误状态码
     */
    public static final String ERROR_CODE = "-1";

    /**
     * 未授权
     */
    public static final String UNAUTHORIZED_CODE = "401";

    /**
     * 禁止访问
     */
    public static final String FORBIDDEN_CODE = "403";

    /**
     * 资源未找到
     */
    public static final String NOT_FOUND_CODE = "404";

    /**
     * 参数错误
     */
    public static final String PARAM_ERROR_CODE = "400";

    /**
     * 服务器错误
     */
    public static final String SERVER_ERROR_CODE = "500";
}
