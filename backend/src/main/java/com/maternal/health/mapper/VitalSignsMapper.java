package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.VitalSigns;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体征监测Mapper接口
 * 功能：提供体征监测数据的数据库访问
 */
@Mapper
public interface VitalSignsMapper extends BaseMapper<VitalSigns> {
}
