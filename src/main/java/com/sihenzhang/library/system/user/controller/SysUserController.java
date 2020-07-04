package com.sihenzhang.library.system.user.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserVOService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/users")
    public Result list(@RequestParam(required = false) String query, @RequestParam Integer current, @RequestParam Integer size) {
        try {
            var page = new Page<SysUser>(current, size);
            var wrapper = Wrappers.<SysUser>lambdaQuery()
                    .select(SysUser::getUserId, SysUser::getUsername, SysUser::getEmail, SysUser::getPhone, SysUser::getStatus);
            if (!StrUtil.isBlank(query))
                wrapper.like(SysUser::getUsername, query);
            var resultPage = sysUserService.page(page, wrapper);
            var data = Dict.create()
                    .set("total", resultPage.getTotal())
                    .set("current", resultPage.getCurrent())
                    .set("users", resultPage.getRecords());
            return ResultFactory.buildSuccessResult("获取用户列表成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取用户列表失败");
        }
    }

    @PutMapping("/user/{id}/status/{status}")
    public Result changeStatus(@PathVariable Long id, @PathVariable Boolean status) {
        try {
            var newUser = SysUser.builder().userId(id).status(status).build();
            var success = sysUserService.updateById(newUser);
            if (success)
                return ResultFactory.buildSuccessResult("编辑用户状态成功", null);
            else
                return ResultFactory.buildFailResult("编辑用户状态失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("编辑用户状态失败");
        }
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody SysUser user) {
        try {
            if (StrUtil.isBlank(user.getEmail()))
                user.setEmail(null);
            if (StrUtil.isBlank(user.getPhone()))
                user.setPhone(null);
            var salt = IdUtil.fastSimpleUUID();
            user.setPassword(SecureUtil.md5(user.getPassword() + salt));
            user.setSalt(salt);
            var success = sysUserService.save(user);
            if (success)
                return ResultFactory.buildResult(ResultCode.CREATED, "添加用户成功", null);
            else
                return ResultFactory.buildFailResult("添加用户失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("添加用户失败");
        }
    }

    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable Long id) {
        try {
            var user = sysUserService.getByIdWithoutPassword(id);
            return ResultFactory.buildSuccessResult("查询用户信息成功", user);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("查询用户信息失败");
        }
    }

    @PutMapping("/user/{id}")
    public Result editUser(@PathVariable Long id, @RequestBody SysUser user) {
        try {
            if (StrUtil.isBlank(user.getEmail()))
                user.setEmail(null);
            if (StrUtil.isBlank(user.getPhone()))
                user.setPhone(null);
            user.setUserId(id);
            var success = sysUserService.updateById(user);
            if (success)
                return ResultFactory.buildSuccessResult("编辑用户成功", null);
            else
                return ResultFactory.buildFailResult("编辑用户失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("编辑用户失败");
        }
    }

    @DeleteMapping("/user/{id}")
    public Result removeUserById(@PathVariable Long id) {
        try {
            var success = sysUserService.removeById(id);
            if (success)
                return ResultFactory.buildSuccessResult("删除用户成功", null);
            else
                return ResultFactory.buildFailResult("删除用户失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("删除用户失败");
        }
    }

}
