package com.maternal.health.enums;

/**
 * @author MaLiang
 * @description 响应码
 * @version 1.0
 * @date 2025-05-20
 */
public class CodeEnums {

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "响应成功";

    public static final String ERROR_CODE = "-1";
    public static final String ERROR_MESSAGE = "响应失败";

    public static final String NO_LOGIN_CODE = "-401";
    public static final String NO_LOGIN_MESSAGE = "未登录";

    public static final String NO_PERM_CODE = "-403";
    public static final String NO_PERM_MESSAGE = "没有权限";

    public static final String NO_ROLE_CODE = "-405";
    public static final String NO_ROLE_MESSAGE = "没有角色";

    public static final String ACCOUNT_DELETED_CODE = "-406";
    public static final String ACCOUNT_DELETED_MESSAGE = "该账户已被删除";

    public static final String ACCOUNT_DISABLED_CODE = "-407";
    public static final String ACCOUNT_DISABLED_MESSAGE = "该账户已被禁用";

}