package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.AddVitalSignsDTO;
import com.maternal.health.entity.VitalSigns;
import com.maternal.health.mapper.VitalSignsMapper;
import com.maternal.health.service.VitalSignsService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.utils.HealthUtil;
import com.maternal.health.vo.VitalSignsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 体征监测服务实现类
 * 功能：实现体征监测相关的业务逻辑
 */
@Slf4j
@Service
public class VitalSignsServiceImpl implements VitalSignsService {

    @Autowired
    private VitalSignsMapper vitalSignsMapper;

    /**
     * 添加体征记录
     * 功能：添加一条体征监测记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addVitalSigns(AddVitalSignsDTO addVitalSignsDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        VitalSigns vitalSigns = BeanCopyUtil.copyProperties(addVitalSignsDTO, VitalSigns.class);
        vitalSigns.setUserId(userId);

        vitalSignsMapper.insert(vitalSigns);
        log.info("体征记录添加成功：userId={}, vitalSignsId={}", userId, vitalSigns.getId());
    }

    /**
     * 更新体征记录
     * 功能：更新已有的体征监测记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVitalSigns(AddVitalSignsDTO addVitalSignsDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        if (addVitalSignsDTO.getId() == null) {
            throw new BusinessException("记录ID不能为空");
        }

        VitalSigns existingRecord = vitalSignsMapper.selectById(addVitalSignsDTO.getId());
        if (existingRecord == null) {
            throw new BusinessException("体征记录不存在");
        }

        if (!existingRecord.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该体征记录");
        }

        VitalSigns vitalSigns = BeanCopyUtil.copyProperties(addVitalSignsDTO, VitalSigns.class);
        vitalSigns.setUserId(userId);

        vitalSignsMapper.updateById(vitalSigns);
        log.info("体征记录更新成功：userId={}, vitalSignsId={}", userId, vitalSigns.getId());
    }

    /**
     * 获取指定日期的体征记录
     * 功能：查询指定日期的体征监测记录
     */
    @Override
    public VitalSignsVO getVitalSignsByDate(LocalDate recordDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<VitalSigns> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VitalSigns::getUserId, userId);
        wrapper.eq(VitalSigns::getRecordDate, recordDate);
        wrapper.orderByDesc(VitalSigns::getCreateTime);
        wrapper.last("LIMIT 1");

        VitalSigns vitalSigns = vitalSignsMapper.selectOne(wrapper);
        if (vitalSigns == null) {
            return null;
        }

        return convertToVO(vitalSigns, null);
    }

    /**
     * 获取最近的体征记录
     * 功能：查询当前用户最近的一条体征记录
     */
    @Override
    public VitalSignsVO getLatestVitalSigns() {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<VitalSigns> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VitalSigns::getUserId, userId);
        wrapper.orderByDesc(VitalSigns::getRecordDate);
        wrapper.orderByDesc(VitalSigns::getCreateTime);
        wrapper.last("LIMIT 1");

        VitalSigns vitalSigns = vitalSignsMapper.selectOne(wrapper);
        if (vitalSigns == null) {
            return null;
        }

        // 获取上一条记录用于计算体重变化
        VitalSigns previousRecord = getPreviousRecord(userId, vitalSigns.getRecordDate());

        return convertToVO(vitalSigns, previousRecord);
    }

    /**
     * 获取指定日期范围的体征记录
     * 功能：查询当前用户在指定日期范围内的所有体征记录
     */
    @Override
    public List<VitalSignsVO> getVitalSignsByDateRange(LocalDate startDate, LocalDate endDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<VitalSigns> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VitalSigns::getUserId, userId);
        wrapper.between(VitalSigns::getRecordDate, startDate, endDate);
        wrapper.orderByDesc(VitalSigns::getRecordDate);

        List<VitalSigns> vitalSignsList = vitalSignsMapper.selectList(wrapper);

        return vitalSignsList.stream()
                .map(vitalSigns -> {
                    VitalSigns previousRecord = getPreviousRecord(userId, vitalSigns.getRecordDate());
                    return convertToVO(vitalSigns, previousRecord);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取最近N天的体征记录
     * 功能：查询当前用户最近N天的体征记录
     */
    @Override
    public List<VitalSignsVO> getRecentVitalSigns(Integer days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        return getVitalSignsByDateRange(startDate, endDate);
    }

    /**
     * 删除体征记录
     * 功能：删除指定的体征监测记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVitalSigns(Long vitalSignsId) {
        Long userId = StpUtil.getLoginIdAsLong();

        VitalSigns vitalSigns = vitalSignsMapper.selectById(vitalSignsId);
        if (vitalSigns == null) {
            throw new BusinessException("体征记录不存在");
        }

        if (!vitalSigns.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该体征记录");
        }

        vitalSignsMapper.deleteById(vitalSignsId);
        log.info("体征记录删除成功：userId={}, vitalSignsId={}", userId, vitalSignsId);
    }

    /**
     * 获取上一条记录
     * 功能：获取指定日期之前的最近一条记录
     */
    private VitalSigns getPreviousRecord(Long userId, LocalDate currentDate) {
        LambdaQueryWrapper<VitalSigns> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VitalSigns::getUserId, userId);
        wrapper.lt(VitalSigns::getRecordDate, currentDate);
        wrapper.orderByDesc(VitalSigns::getRecordDate);
        wrapper.last("LIMIT 1");

        return vitalSignsMapper.selectOne(wrapper);
    }

    /**
     * 转换为VO并添加状态文本
     * 功能：将实体类转换为VO，并添加状态文本描述
     */
    private VitalSignsVO convertToVO(VitalSigns vitalSigns, VitalSigns previousRecord) {
        VitalSignsVO vo = BeanCopyUtil.copyProperties(vitalSigns, VitalSignsVO.class);

        // 添加状态文本
        vo.setBloodPressureStatus(HealthUtil.getBloodPressureStatus(
                vitalSigns.getSystolicPressure(), vitalSigns.getDiastolicPressure()));
        vo.setTemperatureStatus(HealthUtil.getTemperatureStatus(vitalSigns.getTemperature()));
        vo.setHeartRateStatus(HealthUtil.getHeartRateStatus(vitalSigns.getHeartRate()));
        vo.setBloodSugarStatus(HealthUtil.getBloodSugarStatus(vitalSigns.getBloodSugar()));

        // 计算体重变化
        if (previousRecord != null && previousRecord.getWeight() != null && vitalSigns.getWeight() != null) {
            BigDecimal weightChange = vitalSigns.getWeight().subtract(previousRecord.getWeight());
            vo.setWeightChange(weightChange);
        }

        return vo;
    }
}
