package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.AddSymptomRecordDTO;
import com.maternal.health.entity.SymptomRecord;
import com.maternal.health.mapper.SymptomRecordMapper;
import com.maternal.health.service.SymptomRecordService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.utils.HealthUtil;
import com.maternal.health.vo.SymptomRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 症状记录服务实现类
 * 功能：实现症状记录相关的业务逻辑
 */
@Slf4j
@Service
public class SymptomRecordServiceImpl implements SymptomRecordService {

    @Autowired
    private SymptomRecordMapper symptomRecordMapper;

    /**
     * 添加症状记录
     * 功能：添加一条症状记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSymptomRecord(AddSymptomRecordDTO addSymptomRecordDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        SymptomRecord symptomRecord = BeanCopyUtil.copyProperties(addSymptomRecordDTO, SymptomRecord.class);
        symptomRecord.setUserId(userId);

        symptomRecordMapper.insert(symptomRecord);
        log.info("症状记录添加成功：userId={}, symptomRecordId={}", userId, symptomRecord.getId());
    }

    /**
     * 更新症状记录
     * 功能：更新已有的症状记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSymptomRecord(AddSymptomRecordDTO addSymptomRecordDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        if (addSymptomRecordDTO.getId() == null) {
            throw new BusinessException("记录ID不能为空");
        }

        SymptomRecord existingRecord = symptomRecordMapper.selectById(addSymptomRecordDTO.getId());
        if (existingRecord == null) {
            throw new BusinessException("症状记录不存在");
        }

        if (!existingRecord.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该症状记录");
        }

        SymptomRecord symptomRecord = BeanCopyUtil.copyProperties(addSymptomRecordDTO, SymptomRecord.class);
        symptomRecord.setUserId(userId);

        symptomRecordMapper.updateById(symptomRecord);
        log.info("症状记录更新成功：userId={}, symptomRecordId={}", userId, symptomRecord.getId());
    }

    /**
     * 获取指定日期的症状记录
     * 功能：查询当前用户在指定日期的所有症状记录
     */
    @Override
    public List<SymptomRecordVO> getSymptomRecordsByDate(LocalDate recordDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<SymptomRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SymptomRecord::getUserId, userId);
        wrapper.eq(SymptomRecord::getRecordDate, recordDate);
        wrapper.orderByDesc(SymptomRecord::getCreateTime);

        List<SymptomRecord> symptomRecords = symptomRecordMapper.selectList(wrapper);

        return symptomRecords.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定日期范围的症状记录
     * 功能：查询当前用户在指定日期范围内的所有症状记录
     */
    @Override
    public List<SymptomRecordVO> getSymptomRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<SymptomRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SymptomRecord::getUserId, userId);
        wrapper.between(SymptomRecord::getRecordDate, startDate, endDate);
        wrapper.orderByDesc(SymptomRecord::getRecordDate);
        wrapper.orderByDesc(SymptomRecord::getCreateTime);

        List<SymptomRecord> symptomRecords = symptomRecordMapper.selectList(wrapper);

        return symptomRecords.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取最近N天的症状记录
     * 功能：查询当前用户最近N天的症状记录
     */
    @Override
    public List<SymptomRecordVO> getRecentSymptomRecords(Integer days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        return getSymptomRecordsByDateRange(startDate, endDate);
    }

    /**
     * 获取症状详情
     * 功能：根据ID获取症状记录详情
     */
    @Override
    public SymptomRecordVO getSymptomRecordById(Long symptomRecordId) {
        Long userId = StpUtil.getLoginIdAsLong();

        SymptomRecord symptomRecord = symptomRecordMapper.selectById(symptomRecordId);
        if (symptomRecord == null) {
            throw new BusinessException("症状记录不存在");
        }

        if (!symptomRecord.getUserId().equals(userId)) {
            throw new BusinessException("无权查看该症状记录");
        }

        return convertToVO(symptomRecord);
    }

    /**
     * 删除症状记录
     * 功能：删除指定的症状记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSymptomRecord(Long symptomRecordId) {
        Long userId = StpUtil.getLoginIdAsLong();

        SymptomRecord symptomRecord = symptomRecordMapper.selectById(symptomRecordId);
        if (symptomRecord == null) {
            throw new BusinessException("症状记录不存在");
        }

        if (!symptomRecord.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该症状记录");
        }

        symptomRecordMapper.deleteById(symptomRecordId);
        log.info("症状记录删除成功：userId={}, symptomRecordId={}", userId, symptomRecordId);
    }

    /**
     * 转换为VO并添加文字描述
     * 功能：将实体类转换为VO，并添加文字描述
     */
    private SymptomRecordVO convertToVO(SymptomRecord symptomRecord) {
        SymptomRecordVO vo = BeanCopyUtil.copyProperties(symptomRecord, SymptomRecordVO.class);

        // 添加文字描述
        vo.setSymptomTypeText(HealthUtil.getSymptomTypeText(symptomRecord.getSymptomType()));
        vo.setSeverityText(HealthUtil.getSeverityText(symptomRecord.getSeverity()));
        vo.setSeekMedicalText(HealthUtil.getSeekMedicalText(symptomRecord.getSeekMedical()));

        return vo;
    }
}
