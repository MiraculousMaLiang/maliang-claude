package com.maternal.health.controller;

import com.maternal.health.common.Result;
import com.maternal.health.dto.AddVitalSignsDTO;
import com.maternal.health.service.VitalSignsService;
import com.maternal.health.vo.VitalSignsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 体征监测控制器
 * 功能：处理体征监测相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/vital-signs")
public class VitalSignsController {

    @Autowired
    private VitalSignsService vitalSignsService;

    /**
     * 添加体征记录
     * 功能：添加一条体征监测记录
     */
    @PostMapping
    public Result<Void> addVitalSigns(@Validated @RequestBody AddVitalSignsDTO addVitalSignsDTO) {
        vitalSignsService.addVitalSigns(addVitalSignsDTO);
        return Result.success();
    }

    /**
     * 更新体征记录
     * 功能：更新已有的体征监测记录
     */
    @PutMapping
    public Result<Void> updateVitalSigns(@Validated @RequestBody AddVitalSignsDTO addVitalSignsDTO) {
        vitalSignsService.updateVitalSigns(addVitalSignsDTO);
        return Result.success();
    }

    /**
     * 获取指定日期的体征记录
     * 功能：查询指定日期的体征监测记录
     */
    @GetMapping("/date/{recordDate}")
    public Result<VitalSignsVO> getVitalSignsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recordDate) {
        VitalSignsVO vitalSigns = vitalSignsService.getVitalSignsByDate(recordDate);
        return Result.success(vitalSigns);
    }

    /**
     * 获取最近的体征记录
     * 功能：查询当前用户最近的一条体征记录
     */
    @GetMapping("/latest")
    public Result<VitalSignsVO> getLatestVitalSigns() {
        VitalSignsVO vitalSigns = vitalSignsService.getLatestVitalSigns();
        return Result.success(vitalSigns);
    }

    /**
     * 获取指定日期范围的体征记录
     * 功能：查询当前用户在指定日期范围内的所有体征记录
     */
    @GetMapping("/range")
    public Result<List<VitalSignsVO>> getVitalSignsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<VitalSignsVO> vitalSignsList = vitalSignsService.getVitalSignsByDateRange(startDate, endDate);
        return Result.success(vitalSignsList);
    }

    /**
     * 获取最近N天的体征记录
     * 功能：查询当前用户最近N天的体征记录
     */
    @GetMapping("/recent")
    public Result<List<VitalSignsVO>> getRecentVitalSigns(@RequestParam(defaultValue = "7") Integer days) {
        List<VitalSignsVO> vitalSignsList = vitalSignsService.getRecentVitalSigns(days);
        return Result.success(vitalSignsList);
    }

    /**
     * 删除体征记录
     * 功能：删除指定的体征监测记录
     */
    @DeleteMapping("/{vitalSignsId}")
    public Result<Void> deleteVitalSigns(@PathVariable Long vitalSignsId) {
        vitalSignsService.deleteVitalSigns(vitalSignsId);
        return Result.success();
    }
}
