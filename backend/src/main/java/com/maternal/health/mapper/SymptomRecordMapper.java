package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.SymptomRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 症状记录Mapper接口
 * 功能：提供症状记录数据的数据库访问
 */
@Mapper
public interface SymptomRecordMapper extends BaseMapper<SymptomRecord> {
}
