package com.maternal.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maternal.health.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * 功能：用户表的数据访问层，继承MyBatis Plus的BaseMapper提供基础CRUD方法
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
