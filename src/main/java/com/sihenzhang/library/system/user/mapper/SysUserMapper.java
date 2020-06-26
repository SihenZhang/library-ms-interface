package com.sihenzhang.library.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.entity.SysUserVO;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT user_id,username,phone,active FROM sys_user WHERE status=0")
    IPage<SysUserVO> selectPageVO(Page<SysUserVO> page);

}
