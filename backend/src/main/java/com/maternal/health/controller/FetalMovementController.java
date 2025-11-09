package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.AddFetalMovementDTO;
import com.maternal.health.service.FetalMovementService;
import com.maternal.health.vo.FetalMovementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 胎动记录控制器
 * 功能：处理胎动记录相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/fetal-movement")
public class FetalMovementController {

    @Autowired
    private FetalMovementService fetalMovementService;

    /**
     * 添加胎动记录
     * 功能：添加一条胎动记录
     */
    @PostMapping
    public R<Void> addFetalMovement(@Validated @RequestBody AddFetalMovementDTO addFetalMovementDTO) {
        fetalMovementService.addFetalMovement(addFetalMovementDTO);
        return R.ok("");
    }

    /**
     * 获取指定日期的胎动记录
     * 功能：查询当前用户在指定日期的所有胎动记录
     */
    @GetMapping("/date/{recordDate}")
    public R<List<FetalMovementVO>> getFetalMovementsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recordDate) {
        List<FetalMovementVO> fetalMovements = fetalMovementService.getFetalMovementsByDate(recordDate);
        return R.ok(fetalMovements);
    }

    /**
     * 获取指定日期范围的胎动记录
     * 功能：查询当前用户在指定日期范围内的所有胎动记录
     */
    @GetMapping("/range")
    public R<List<FetalMovementVO>> getFetalMovementsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<FetalMovementVO> fetalMovements = fetalMovementService.getFetalMovementsByDateRange(startDate, endDate);
        return R.ok(fetalMovements);
    }

    /**
     * 获取最近7天的胎动记录
     * 功能：查询当前用户最近7天的胎动记录
     */
    @GetMapping("/recent")
    public R<List<FetalMovementVO>> getRecentFetalMovements() {
        List<FetalMovementVO> fetalMovements = fetalMovementService.getRecentFetalMovements();
        return R.ok(fetalMovements);
    }

    /**
     * 删除胎动记录
     * 功能：删除指定的胎动记录
     */
    @DeleteMapping("/{fetalMovementId}")
    public R<Void> deleteFetalMovement(@PathVariable Long fetalMovementId) {
        fetalMovementService.deleteFetalMovement(fetalMovementId);
        return R.ok("");
    }

    /**
     * 获取指定日期的胎动统计
     * 功能：统计指定日期的胎动次数和总时长
     */
    @GetMapping("/statistics/{recordDate}")
    public R<FetalMovementVO> getFetalMovementStatistics(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recordDate) {
        FetalMovementVO statistics = fetalMovementService.getFetalMovementStatistics(recordDate);
        return R.ok(statistics);
    }
}
