package com.sihenzhang.library.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.entity.SysUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT user_id,username,email,phone,status FROM sys_user")
    IPage<SysUserVO> selectPageVO(Page<SysUserVO> page);

    @Select("SELECT user_id,username,email,phone,status FROM sys_user ${ew.customSqlSegment}")
    IPage<SysUserVO> selectPageVOWithWrapper(Page<SysUserVO> page, @Param(Constants.WRAPPER) Wrapper<SysUserVO> wrapper);

    @Select("SELECT user_id,username,email,phone,status FROM sys_user WHERE user_id=#{id}")
    SysUserVO selectVOById(Long id);

}
