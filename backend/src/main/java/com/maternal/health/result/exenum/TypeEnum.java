package com.maternal.health.result.exenum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author MaLiang
 * @version 1.0
 * @date 2024-05-23 11:11
 */
@NoArgsConstructor
@AllArgsConstructor
public enum TypeEnum {
    /**
     * 未知
     */
    UNKNOWN(-1, "UNKNOWN"),
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 客户端
     */
    CLIENT(1, "CLIENT"),
    /**
     * 服务端
     */
    SERVER(2, "SERVER");
    /**
     * 指数
     */
    private Integer index;
    /**
     * 描述
     */
    private String description;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
