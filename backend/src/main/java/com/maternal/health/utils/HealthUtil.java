package com.maternal.health.utils;

import java.math.BigDecimal;

/**
 * 健康工具类
 * 功能：提供健康指标判断和文本转换方法
 */
public class HealthUtil {

    /**
     * 判断血压状态
     * 功能：根据收缩压和舒张压判断血压状态
     */
    public static String getBloodPressureStatus(Integer systolic, Integer diastolic) {
        if (systolic == null || diastolic == null) {
            return "未记录";
        }

        // 正常范围：收缩压90-140，舒张压60-90
        if (systolic >= 140 || diastolic >= 90) {
            return "偏高";
        } else if (systolic < 90 || diastolic < 60) {
            return "偏低";
        } else {
            return "正常";
        }
    }

    /**
     * 判断体温状态
     * 功能：根据体温判断体温状态
     */
    public static String getTemperatureStatus(BigDecimal temperature) {
        if (temperature == null) {
            return "未记录";
        }

        // 正常范围：36.0-37.3℃
        if (temperature.compareTo(new BigDecimal("37.3")) > 0) {
            return "偏高";
        } else if (temperature.compareTo(new BigDecimal("36.0")) < 0) {
            return "偏低";
        } else {
            return "正常";
        }
    }

    /**
     * 判断心率状态
     * 功能：根据心率判断心率状态
     */
    public static String getHeartRateStatus(Integer heartRate) {
        if (heartRate == null) {
            return "未记录";
        }

        // 正常范围：60-100次/分钟
        if (heartRate > 100) {
            return "偏快";
        } else if (heartRate < 60) {
            return "偏慢";
        } else {
            return "正常";
        }
    }

    /**
     * 判断血糖状态
     * 功能：根据血糖值判断血糖状态
     */
    public static String getBloodSugarStatus(BigDecimal bloodSugar) {
        if (bloodSugar == null) {
            return "未记录";
        }

        // 正常范围：3.9-6.1 mmol/L（空腹）
        if (bloodSugar.compareTo(new BigDecimal("6.1")) > 0) {
            return "偏高";
        } else if (bloodSugar.compareTo(new BigDecimal("3.9")) < 0) {
            return "偏低";
        } else {
            return "正常";
        }
    }

    /**
     * 获取症状类型文本
     * 功能：将症状类型码转换为文字描述
     */
    public static String getSymptomTypeText(Integer symptomType) {
        if (symptomType == null) {
            return "未知";
        }
        switch (symptomType) {
            case 1:
                return "头痛";
            case 2:
                return "恶心呕吐";
            case 3:
                return "腹痛";
            case 4:
                return "水肿";
            case 5:
                return "其他";
            default:
                return "未知";
        }
    }

    /**
     * 获取严重程度文本
     * 功能：将严重程度码转换为文字描述
     */
    public static String getSeverityText(Integer severity) {
        if (severity == null) {
            return "未知";
        }
        switch (severity) {
            case 1:
                return "轻微";
            case 2:
                return "中等";
            case 3:
                return "严重";
            default:
                return "未知";
        }
    }

    /**
     * 获取是否就医文本
     * 功能：将是否就医标记转换为文字描述
     */
    public static String getSeekMedicalText(Integer seekMedical) {
        if (seekMedical == null) {
            return "未记录";
        }
        return seekMedical == 1 ? "已就医" : "未就医";
    }
}
