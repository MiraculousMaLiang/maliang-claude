package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.AddPregnancyInfoDTO;
import com.maternal.health.service.PregnancyInfoService;
import com.maternal.health.vo.PregnancyInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 孕期信息控制器
 * 功能：处理孕期信息相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/pregnancy")
public class PregnancyInfoController {

    @Autowired
    private PregnancyInfoService pregnancyInfoService;

    /**
     * 获取当前用户的孕期信息
     * 功能：获取当前登录用户的最新孕期信息
     */
    @GetMapping("/info")
    public R<PregnancyInfoVO> getCurrentPregnancyInfo() {
        PregnancyInfoVO pregnancyInfo = pregnancyInfoService.getCurrentPregnancyInfo();
        return R.ok(pregnancyInfo);
    }

    /**
     * 添加或更新孕期信息
     * 功能：如果ID存在则更新，不存在则新增
     */
    @PostMapping("/info")
    public R<Void> saveOrUpdatePregnancyInfo(@Validated @RequestBody AddPregnancyInfoDTO addPregnancyInfoDTO) {
        pregnancyInfoService.saveOrUpdatePregnancyInfo(addPregnancyInfoDTO);
        return R.ok();
    }

    /**
     * 更新孕期计算字段
     * 功能：根据末次月经日期更新预产期、孕周等计算字段
     */
    @PutMapping("/info/{pregnancyId}/calculate")
    public R<Void> updateCalculatedFields(@PathVariable Long pregnancyId) {
        pregnancyInfoService.updateCalculatedFields(pregnancyId);
        return R.ok();
    }

    /**
     * 删除孕期信息
     * 功能：逻辑删除指定的孕期信息
     */
    @DeleteMapping("/info/{pregnancyId}")
    public R<Void> deletePregnancyInfo(@PathVariable Long pregnancyId) {
        pregnancyInfoService.deletePregnancyInfo(pregnancyId);
        return R.ok();
    }
}
