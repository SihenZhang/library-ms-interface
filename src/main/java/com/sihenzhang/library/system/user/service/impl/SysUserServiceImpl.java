package com.sihenzhang.library.system.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.system.user.entity.SysUser;
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

    public boolean saveUser(SysUser user) {
        if (StrUtil.isBlank(user.getEmail()))
            user.setEmail(null);
        if (StrUtil.isBlank(user.getPhone()))
            user.setPhone(null);
        var salt = IdUtil.fastSimpleUUID();
        user.setPassword(SecureUtil.md5(user.getPassword() + salt));
        user.setSalt(salt);
        return save(user);
    }

    public SysUser getByIdWithoutPassword(Long id) {
        return getOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getUserId, SysUser::getUsername, SysUser::getEmail, SysUser::getPhone, SysUser::getStatus).eq(SysUser::getUserId, id), false);
    }

    public boolean updateUser(SysUser user) {
        if (StrUtil.isBlank(user.getEmail()))
            user.setEmail(null);
        if (StrUtil.isBlank(user.getPhone()))
            user.setPhone(null);
        return updateById(user);
    }

}
