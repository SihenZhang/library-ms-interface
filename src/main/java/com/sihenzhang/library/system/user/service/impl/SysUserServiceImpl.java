package com.sihenzhang.library.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.entity.SysUserVO;
import com.sihenzhang.library.system.user.mapper.SysUserMapper;
import com.sihenzhang.library.system.user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    public SysUser getByUsername(String username) {
        return getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username), false);
    }

    public boolean isExist(String username) {
        return count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)) != 0;
    }

    public IPage<SysUserVO> selectUserPage(Page<SysUserVO> page) {
        return this.baseMapper.selectPageVO(page);
    }

}
