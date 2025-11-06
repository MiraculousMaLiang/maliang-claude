package com.maternal.health.service;

import com.maternal.health.dto.AddSymptomRecordDTO;
import com.maternal.health.vo.SymptomRecordVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 症状记录服务接口
 * 功能：提供症状记录相关的业务方法
 */
public interface SymptomRecordService {

    /**
     * 添加症状记录
     * 功能：添加一条症状记录
     */
    void addSymptomRecord(AddSymptomRecordDTO addSymptomRecordDTO);

    /**
     * 更新症状记录
     * 功能：更新已有的症状记录
     */
    void updateSymptomRecord(AddSymptomRecordDTO addSymptomRecordDTO);

    /**
     * 获取指定日期的症状记录
     * 功能：查询当前用户在指定日期的所有症状记录
     */
    List<SymptomRecordVO> getSymptomRecordsByDate(LocalDate recordDate);

    /**
     * 获取指定日期范围的症状记录
     * 功能：查询当前用户在指定日期范围内的所有症状记录
     */
    List<SymptomRecordVO> getSymptomRecordsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 获取最近N天的症状记录
     * 功能：查询当前用户最近N天的症状记录
     */
    List<SymptomRecordVO> getRecentSymptomRecords(Integer days);

    /**
     * 获取症状详情
     * 功能：根据ID获取症状记录详情
     */
    SymptomRecordVO getSymptomRecordById(Long symptomRecordId);

    /**
     * 删除症状记录
     * 功能：删除指定的症状记录
     */
    void deleteSymptomRecord(Long symptomRecordId);
}
