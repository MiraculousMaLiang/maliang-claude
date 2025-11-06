package com.maternal.health.service;

import com.maternal.health.dto.AddPregnancyInfoDTO;
import com.maternal.health.vo.PregnancyInfoVO;

/**
 * 孕期信息服务接口
 * 功能：提供孕期信息管理相关的业务方法
 */
public interface PregnancyInfoService {

    /**
     * 获取当前用户的孕期信息
     * 功能：获取当前登录用户的最新孕期信息
     */
    PregnancyInfoVO getCurrentPregnancyInfo();

    /**
     * 添加或更新孕期信息
     * 功能：如果没有孕期信息则添加，有则更新
     */
    void saveOrUpdatePregnancyInfo(AddPregnancyInfoDTO addPregnancyInfoDTO);

    /**
     * 更新孕期计算字段
     * 功能：根据末次月经日期更新预产期、孕周等计算字段
     */
    void updateCalculatedFields(Long pregnancyId);

    /**
     * 删除孕期信息
     * 功能：删除指定的孕期信息
     */
    void deletePregnancyInfo(Long pregnancyId);
}
