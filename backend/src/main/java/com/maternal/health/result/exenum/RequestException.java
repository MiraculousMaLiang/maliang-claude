package com.maternal.health.result.exenum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author MaLiang
 * @version 1.0
 * @date 2024-05-23 11:12
 */
@AllArgsConstructor
@NoArgsConstructor
public enum RequestException {

    /**
     * 非法授权
     */
    FORBIDDEN(401, "非法授权!", TypeEnum.CLIENT.getDescription()),
    /**
     * 非法访问
     */
    UNAUTHORIZED(403, "非法访问!", TypeEnum.CLIENT.getDescription()),
    /**
     * 登录失败
     */
    LOGIN_ERROR(402, "登录失败,请检查用户名或密码是否正确!", TypeEnum.CLIENT.getDescription()),
    /**
     * 找不到资源
     */
    NOT_FOUND(404, "找不到资源!", TypeEnum.CLIENT.getDescription()),
    /**
     * 注册失败
     */
    REGISTER_ERROR(405, "注册失败,请检查数据是否符合规范!", TypeEnum.CLIENT.getDescription()),
    /**
     * 文件超出范围
     */
    FILE_BEYOND_MAX_SIZE(406, "文件超出范围!", TypeEnum.CLIENT.getDescription()),
    /**
     * token异常
     */
    TOKEN_ERROR(407, "非法token", TypeEnum.CLIENT.getDescription()),
    /**
     * 退出登录失败
     */
    LOGOUT_ERROR(408, "退出登录失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 该数据数据已经存在
     */
    DATA_EXISTS_ERROR(409, "该数据已经存在", TypeEnum.SERVER.getDescription()),
    /**
     * 手机号已被占用
     */
    PHONE_EXISTS_ERROR(410, "手机号已被占用", TypeEnum.CLIENT.getDescription()),
    /**
     * 验证码校验失败
     */
    PHONE_CODE_ERROR(411, "验证码错误", TypeEnum.CLIENT.getDescription()),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(500, "未知错误,请联系网站管理员!", TypeEnum.UNKNOWN.getDescription()),
    /**
     * 查询数据时遇到问题
     */
    SELECT_ERROR(501, "数据查询失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 添加数据时遇到问题
     */
    INSERT_ERROR(502, "数据添加失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 修改数据时遇到问题
     */
    UPDATE_ERROR(503, "数据修改失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 删除数据时遇到问题
     */
    DELETE_ERROR(504, "数据删除失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 文件上传失败
     */
    FILE_ERROR(505, "文件上传失败!", TypeEnum.SERVER.getDescription()),
    /**
     * 空指针异常
     */
    NULL_POINTER_ERROR(506, "空指针异常!", TypeEnum.SERVER.getDescription()),
    /**
     * 验证码生成异常
     */
    CAPTCHA_GENERATED_ERROR(507, "验证码生成异常!", TypeEnum.SERVER.getDescription()),

    /**
     * 植物参数不能为空
     */
    Plant_ParamNull(509, "植物参数不能为空!", TypeEnum.CLIENT.getDescription()),
    /**
     * family参数不能为空
     */
    Family_ParamNull(510, "family参数不能为空!", TypeEnum.CLIENT.getDescription()),
    /**
     * 验证码校验异常
     */
    CAPTCHA_VERIFY_ERROR(508, "验证码校验异常!", TypeEnum.SERVER.getDescription());



    /**
     * 错误代码
     */
    private Integer status;
    /**
     * 消息
     */
    private String message;
    /**
     * 错误类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
