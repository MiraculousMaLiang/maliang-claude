package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.AddFetalMovementDTO;
import com.maternal.health.entity.FetalMovement;
import com.maternal.health.mapper.FetalMovementMapper;
import com.maternal.health.service.FetalMovementService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.utils.PregnancyUtil;
import com.maternal.health.vo.FetalMovementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 胎动记录服务实现类
 * 功能：实现胎动记录管理相关的业务逻辑
 */
@Slf4j
@Service
public class FetalMovementServiceImpl implements FetalMovementService {

    @Autowired
    private FetalMovementMapper fetalMovementMapper;

    /**
     * 添加胎动记录
     * 功能：添加一条胎动记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFetalMovement(AddFetalMovementDTO addFetalMovementDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        FetalMovement fetalMovement = BeanCopyUtil.copyProperties(addFetalMovementDTO, FetalMovement.class);
        fetalMovement.setUserId(userId);

        fetalMovementMapper.insert(fetalMovement);
        log.info("胎动记录添加成功：userId={}, fetalMovementId={}", userId, fetalMovement.getId());
    }

    /**
     * 获取指定日期的胎动记录
     * 功能：查询当前用户在指定日期的所有胎动记录
     */
    @Override
    public List<FetalMovementVO> getFetalMovementsByDate(LocalDate recordDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<FetalMovement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FetalMovement::getUserId, userId);
        wrapper.eq(FetalMovement::getRecordDate, recordDate);
        wrapper.orderByAsc(FetalMovement::getRecordTime);

        List<FetalMovement> fetalMovements = fetalMovementMapper.selectList(wrapper);

        return fetalMovements.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定日期范围的胎动记录
     * 功能：查询当前用户在指定日期范围内的所有胎动记录
     */
    @Override
    public List<FetalMovementVO> getFetalMovementsByDateRange(LocalDate startDate, LocalDate endDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<FetalMovement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FetalMovement::getUserId, userId);
        wrapper.between(FetalMovement::getRecordDate, startDate, endDate);
        wrapper.orderByDesc(FetalMovement::getRecordDate);
        wrapper.orderByDesc(FetalMovement::getRecordTime);

        List<FetalMovement> fetalMovements = fetalMovementMapper.selectList(wrapper);

        return fetalMovements.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取最近7天的胎动记录
     * 功能：查询当前用户最近7天的胎动记录
     */
    @Override
    public List<FetalMovementVO> getRecentFetalMovements() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        return getFetalMovementsByDateRange(sevenDaysAgo, today);
    }

    /**
     * 删除胎动记录
     * 功能：删除指定的胎动记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFetalMovement(Long fetalMovementId) {
        Long userId = StpUtil.getLoginIdAsLong();

        FetalMovement fetalMovement = fetalMovementMapper.selectById(fetalMovementId);
        if (fetalMovement == null) {
            throw new BusinessException("胎动记录不存在");
        }

        if (!fetalMovement.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该胎动记录");
        }

        fetalMovementMapper.deleteById(fetalMovementId);
        log.info("胎动记录删除成功：userId={}, fetalMovementId={}", userId, fetalMovementId);
    }

    /**
     * 获取指定日期的胎动统计
     * 功能：统计指定日期的胎动次数和总时长
     */
    @Override
    public FetalMovementVO getFetalMovementStatistics(LocalDate recordDate) {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<FetalMovement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FetalMovement::getUserId, userId);
        wrapper.eq(FetalMovement::getRecordDate, recordDate);

        List<FetalMovement> fetalMovements = fetalMovementMapper.selectList(wrapper);

        if (fetalMovements.isEmpty()) {
            return null;
        }

        // 统计总次数和总时长
        int totalCount = fetalMovements.size();
        int totalDuration = fetalMovements.stream()
                .mapToInt(FetalMovement::getDuration)
                .sum();

        // 创建统计VO
        FetalMovementVO statisticsVO = new FetalMovementVO();
        statisticsVO.setRecordDate(recordDate);
        statisticsVO.setMovementCount(totalCount);
        statisticsVO.setDuration(totalDuration);

        return statisticsVO;
    }

    /**
     * 转换为VO并添加文字描述
     * 功能：将实体类转换为VO，并添加文字描述
     */
    private FetalMovementVO convertToVO(FetalMovement fetalMovement) {
        FetalMovementVO vo = BeanCopyUtil.copyProperties(fetalMovement, FetalMovementVO.class);

        // 添加文字描述
        vo.setTimePeriodText(PregnancyUtil.getTimePeriodText(fetalMovement.getTimePeriod()));
        vo.setStrengthText(PregnancyUtil.getStrengthText(fetalMovement.getStrength()));

        return vo;
    }
}
