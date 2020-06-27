package com.sihenzhang.library.system.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.entity.SysUserVO;

public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    boolean isExist(String username);

    IPage<SysUserVO> selectUserPage(Page<SysUserVO> page);

    IPage<SysUserVO> selectUserPage(Page<SysUserVO> page, Wrapper<SysUserVO> wrapper);

    SysUserVO selectUserById(Long id);

}
