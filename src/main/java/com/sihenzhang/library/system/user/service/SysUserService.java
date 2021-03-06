package com.sihenzhang.library.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.system.user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    boolean isExist(String username);

    boolean saveUser(SysUser user);

    SysUser getByIdWithoutPassword(Long id);

    boolean updateUser(SysUser user);

}
