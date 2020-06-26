package com.sihenzhang.library.system.security.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.crypto.SecureUtil;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultFactory;
import com.sihenzhang.library.system.security.token.TokenUtil;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody SysUser user) {
        String username = user.getUsername();
        // 数据库中不存在用户
        if (!sysUserService.isExist(username))
            return ResultFactory.buildFailResult("用户不存在");
        // 获取数据库中的同名用户
        SysUser userInDB = sysUserService.getByUsername(username);
        // 用户未被激活
        if (!userInDB.getActive())
            return ResultFactory.buildFailResult("用户未被激活");
        // 对用户输入的密码加盐后加密
        String password = SecureUtil.md5(user.getPassword() + userInDB.getSalt());
        // 密码一致，成功登录
        if (password.equals(userInDB.getPassword())) {
            String token = TokenUtil.sign(user);
            log.info("用户 {} 登录成功", user.getUsername());
            return ResultFactory.buildSuccessResult(Dict.create().set("token", token));
        }
        else
            return ResultFactory.buildFailResult("用户名或密码错误");
    }

}
