package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.EmergencyContact;
import org.apache.ibatis.annotations.Mapper;

/**
 * 紧急联系人Mapper接口
 * 功能：紧急联系人表的数据访问层，继承MyBatis Plus的BaseMapper提供基础CRUD方法
 */
@Mapper
public interface EmergencyContactMapper extends BaseMapper<EmergencyContact> {

}
