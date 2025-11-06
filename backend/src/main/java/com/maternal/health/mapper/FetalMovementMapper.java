package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.FetalMovement;
import org.apache.ibatis.annotations.Mapper;

/**
 * 胎动记录Mapper接口
 * 功能：胎动记录表的数据访问层，继承MyBatis Plus的BaseMapper提供基础CRUD方法
 */
@Mapper
public interface FetalMovementMapper extends BaseMapper<FetalMovement> {

}
