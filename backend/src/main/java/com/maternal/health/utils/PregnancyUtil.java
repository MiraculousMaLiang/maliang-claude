package com.maternal.health.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 孕期计算工具类
 * 功能：提供孕期相关的计算方法（预产期、孕周、孕期状态等）
 */
public class PregnancyUtil {

    /**
     * 计算预产期
     * 功能：根据末次月经日期计算预产期（末次月经日期 + 280天）
     */
    public static LocalDate calculateDueDate(LocalDate lastPeriodDate) {
        if (lastPeriodDate == null) {
            return null;
        }
        return lastPeriodDate.plusDays(280);
    }

    /**
     * 计算当前孕周
     * 功能：根据末次月经日期计算当前孕周
     */
    public static int calculateCurrentWeek(LocalDate lastPeriodDate) {
        if (lastPeriodDate == null) {
            return 0;
        }
        long days = ChronoUnit.DAYS.between(lastPeriodDate, LocalDate.now());
        return (int) (days / 7);
    }

    /**
     * 计算当前孕天
     * 功能：计算距离末次月经的天数
     */
    public static int calculateCurrentDay(LocalDate lastPeriodDate) {
        if (lastPeriodDate == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(lastPeriodDate, LocalDate.now());
    }

    /**
     * 计算距离预产期的天数
     * 功能：计算当前日期距离预产期还有多少天
     */
    public static int calculateDaysToDelivery(LocalDate dueDate) {
        if (dueDate == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    /**
     * 计算孕期状态
     * 功能：根据孕周判断孕期状态（1-孕早期，2-孕中期，3-孕晚期，4-已分娩）
     */
    public static int calculatePregnancyStatus(int currentWeek, LocalDate dueDate) {
        // 已过预产期
        if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
            return 4; // 已分娩
        }

        // 根据孕周判断
        if (currentWeek <= 12) {
            return 1; // 孕早期（1-12周）
        } else if (currentWeek <= 28) {
            return 2; // 孕中期（13-28周）
        } else {
            return 3; // 孕晚期（29周-分娩）
        }
    }

    /**
     * 获取孕期状态描述
     * 功能：将孕期状态码转换为文字描述
     */
    public static String getPregnancyStatusText(Integer pregnancyStatus) {
        if (pregnancyStatus == null) {
            return "未知";
        }
        switch (pregnancyStatus) {
            case 1:
                return "孕早期";
            case 2:
                return "孕中期";
            case 3:
                return "孕晚期";
            case 4:
                return "已分娩";
            default:
                return "未知";
        }
    }

    /**
     * 获取妊娠类型描述
     * 功能：将妊娠类型码转换为文字描述
     */
    public static String getPregnancyTypeText(Integer pregnancyType) {
        if (pregnancyType == null) {
            return "未知";
        }
        switch (pregnancyType) {
            case 1:
                return "单胎";
            case 2:
                return "双胎";
            case 3:
                return "多胎";
            default:
                return "未知";
        }
    }

    /**
     * 获取风险等级描述
     * 功能：将风险等级码转换为文字描述
     */
    public static String getRiskLevelText(Integer riskLevel) {
        if (riskLevel == null) {
            return "未知";
        }
        switch (riskLevel) {
            case 1:
                return "低危";
            case 2:
                return "高危";
            default:
                return "未知";
        }
    }

    /**
     * 获取时间段描述
     * 功能：将时间段码转换为文字描述
     */
    public static String getTimePeriodText(Integer timePeriod) {
        if (timePeriod == null) {
            return "未知";
        }
        switch (timePeriod) {
            case 1:
                return "早上";
            case 2:
                return "中午";
            case 3:
                return "晚上";
            default:
                return "未知";
        }
    }

    /**
     * 获取胎动强度描述
     * 功能：将胎动强度码转换为文字描述
     */
    public static String getStrengthText(Integer strength) {
        if (strength == null) {
            return "未知";
        }
        switch (strength) {
            case 1:
                return "轻微";
            case 2:
                return "适中";
            case 3:
                return "强烈";
            default:
                return "未知";
        }
    }
}
