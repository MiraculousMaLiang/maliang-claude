package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.PregnancyInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 孕期信息Mapper接口
 * 功能：孕期信息表的数据访问层，继承MyBatis Plus的BaseMapper提供基础CRUD方法
 */
@Mapper
public interface PregnancyInfoMapper extends BaseMapper<PregnancyInfo> {

}
