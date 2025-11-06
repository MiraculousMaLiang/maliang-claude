package com.maternal.health.utils;

import cn.hutool.core.bean.BeanUtil;

/**
 * Bean拷贝工具类
 * 功能：提供对象属性拷贝方法，简化DTO、VO、Entity之间的转换
 */
public class BeanCopyUtil {

    /**
     * 拷贝对象属性
     * 功能：将源对象的属性值拷贝到目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return BeanUtil.copyProperties(source, targetClass);
    }

    /**
     * 拷贝对象属性到已存在的目标对象
     * 功能：将源对象的属性值拷贝到已存在的目标对象中
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtil.copyProperties(source, target);
    }
}
