package com.maternal.health.service;

import com.maternal.health.dto.AddVitalSignsDTO;
import com.maternal.health.vo.VitalSignsVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 体征监测服务接口
 * 功能：提供体征监测相关的业务方法
 */
public interface VitalSignsService {

    /**
     * 添加体征记录
     * 功能：添加一条体征监测记录
     */
    void addVitalSigns(AddVitalSignsDTO addVitalSignsDTO);

    /**
     * 更新体征记录
     * 功能：更新已有的体征监测记录
     */
    void updateVitalSigns(AddVitalSignsDTO addVitalSignsDTO);

    /**
     * 获取指定日期的体征记录
     * 功能：查询指定日期的体征监测记录
     */
    VitalSignsVO getVitalSignsByDate(LocalDate recordDate);

    /**
     * 获取最近的体征记录
     * 功能：查询当前用户最近的一条体征记录
     */
    VitalSignsVO getLatestVitalSigns();

    /**
     * 获取指定日期范围的体征记录
     * 功能：查询当前用户在指定日期范围内的所有体征记录
     */
    List<VitalSignsVO> getVitalSignsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 获取最近N天的体征记录
     * 功能：查询当前用户最近N天的体征记录
     */
    List<VitalSignsVO> getRecentVitalSigns(Integer days);

    /**
     * 删除体征记录
     * 功能：删除指定的体征监测记录
     */
    void deleteVitalSigns(Long vitalSignsId);
}
