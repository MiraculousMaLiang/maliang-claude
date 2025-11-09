package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.AddSymptomRecordDTO;
import com.maternal.health.service.SymptomRecordService;
import com.maternal.health.vo.SymptomRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 症状记录控制器
 * 功能：处理症状记录相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/symptom-record")
public class SymptomRecordController {

    @Autowired
    private SymptomRecordService symptomRecordService;

    /**
     * 添加症状记录
     * 功能：添加一条症状记录
     */
    @PostMapping
    public R<Void> addSymptomRecord(@Validated @RequestBody AddSymptomRecordDTO addSymptomRecordDTO) {
        symptomRecordService.addSymptomRecord(addSymptomRecordDTO);
        return R.ok("");
    }

    /**
     * 更新症状记录
     * 功能：更新已有的症状记录
     */
    @PutMapping
    public R<Void> updateSymptomRecord(@Validated @RequestBody AddSymptomRecordDTO addSymptomRecordDTO) {
        symptomRecordService.updateSymptomRecord(addSymptomRecordDTO);
        return R.ok("");
    }

    /**
     * 获取指定日期的症状记录
     * 功能：查询当前用户在指定日期的所有症状记录
     */
    @GetMapping("/date/{recordDate}")
    public R<List<SymptomRecordVO>> getSymptomRecordsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recordDate) {
        List<SymptomRecordVO> symptomRecords = symptomRecordService.getSymptomRecordsByDate(recordDate);
        return R.ok(symptomRecords);
    }

    /**
     * 获取指定日期范围的症状记录
     * 功能：查询当前用户在指定日期范围内的所有症状记录
     */
    @GetMapping("/range")
    public R<List<SymptomRecordVO>> getSymptomRecordsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<SymptomRecordVO> symptomRecords = symptomRecordService.getSymptomRecordsByDateRange(startDate, endDate);
        return R.ok(symptomRecords);
    }

    /**
     * 获取最近N天的症状记录
     * 功能：查询当前用户最近N天的症状记录
     */
    @GetMapping("/recent")
    public R<List<SymptomRecordVO>> getRecentSymptomRecords(@RequestParam(defaultValue = "7") Integer days) {
        List<SymptomRecordVO> symptomRecords = symptomRecordService.getRecentSymptomRecords(days);
        return R.ok(symptomRecords);
    }

    /**
     * 获取症状详情
     * 功能：根据ID获取症状记录详情
     */
    @GetMapping("/{symptomRecordId}")
    public R<SymptomRecordVO> getSymptomRecordById(@PathVariable Long symptomRecordId) {
        SymptomRecordVO symptomRecord = symptomRecordService.getSymptomRecordById(symptomRecordId);
        return R.ok(symptomRecord);
    }

    /**
     * 删除症状记录
     * 功能：删除指定的症状记录
     */
    @DeleteMapping("/{symptomRecordId}")
    public R<Void> deleteSymptomRecord(@PathVariable Long symptomRecordId) {
        symptomRecordService.deleteSymptomRecord(symptomRecordId);
        return R.ok("");
    }
}
