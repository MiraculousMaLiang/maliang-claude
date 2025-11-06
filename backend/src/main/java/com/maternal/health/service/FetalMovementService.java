package com.maternal.health.service;

import com.maternal.health.dto.AddFetalMovementDTO;
import com.maternal.health.vo.FetalMovementVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 胎动记录服务接口
 * 功能：提供胎动记录管理相关的业务方法
 */
public interface FetalMovementService {

    /**
     * 添加胎动记录
     * 功能：添加一条胎动记录
     */
    void addFetalMovement(AddFetalMovementDTO addFetalMovementDTO);

    /**
     * 获取指定日期的胎动记录
     * 功能：查询当前用户在指定日期的所有胎动记录
     */
    List<FetalMovementVO> getFetalMovementsByDate(LocalDate recordDate);

    /**
     * 获取指定日期范围的胎动记录
     * 功能：查询当前用户在指定日期范围内的所有胎动记录
     */
    List<FetalMovementVO> getFetalMovementsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 获取最近7天的胎动记录
     * 功能：查询当前用户最近7天的胎动记录
     */
    List<FetalMovementVO> getRecentFetalMovements();

    /**
     * 删除胎动记录
     * 功能：删除指定的胎动记录
     */
    void deleteFetalMovement(Long fetalMovementId);

    /**
     * 获取指定日期的胎动统计
     * 功能：统计指定日期的胎动次数和总时长
     */
    FetalMovementVO getFetalMovementStatistics(LocalDate recordDate);
}
