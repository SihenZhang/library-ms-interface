package com.sihenzhang.library.system.user.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import com.sihenzhang.library.system.user.entity.SysUser;
import com.sihenzhang.library.system.user.entity.SysUserVO;
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
    public Result list(@RequestParam String query, @RequestParam Integer current, @RequestParam Integer size) {
        Page<SysUserVO> page = new Page<>(current, size);
        IPage<SysUserVO> userIPage;
        if (StrUtil.isBlank(query))
            userIPage = sysUserService.selectUserPage(page);
        else {
            QueryWrapper<SysUserVO> wrapper = new QueryWrapper<>();
            wrapper.like("username", query);
            userIPage = sysUserService.selectUserPage(page, wrapper);
        }
        Dict data = Dict.create()
                .set("total", userIPage.getTotal())
                .set("current", userIPage.getCurrent())
                .set("users", userIPage.getRecords());
        return ResultFactory.buildSuccessResult("获取用户列表成功", data);
    }

    @PutMapping("/user/{id}/status/{status}")
    public Result changeStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        SysUser newUser = SysUser.builder().userId(id).status(status).build();
        boolean success = sysUserService.updateById(newUser);
        if (success)
            return ResultFactory.buildSuccessResult("修改用户状态成功", null);
        else
            return ResultFactory.buildFailResult("修改用户状态失败");
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody SysUser user) {
        if (StrUtil.isBlank(user.getEmail()))
            user.setEmail(null);
        if (StrUtil.isBlank(user.getPhone()))
            user.setPhone(null);
        String salt = IdUtil.fastSimpleUUID();
        user.setPassword(SecureUtil.md5(user.getPassword() + salt));
        user.setSalt(salt);
        boolean success = sysUserService.save(user);
        if (success)
            return ResultFactory.buildResult(ResultCode.CREATED, "添加用户成功", null);
        else
            return ResultFactory.buildFailResult("添加用户失败");
    }

    @GetMapping("/user/{id}")
    public Result getById(@PathVariable("id") Long id) {
        SysUserVO userVO = sysUserService.selectUserById(id);
        return ResultFactory.buildSuccessResult("查询用户信息成功", userVO);
    }

    @PutMapping("/user/{id}")
    public Result editUser(@PathVariable("id") Long id, @RequestBody SysUser user) {
        if (StrUtil.isBlank(user.getEmail()))
            user.setEmail(null);
        if (StrUtil.isBlank(user.getPhone()))
            user.setPhone(null);
        user.setUserId(id);
        boolean success = sysUserService.updateById(user);
        if (success)
            return ResultFactory.buildSuccessResult("更新用户成功", null);
        else
            return ResultFactory.buildFailResult("更新用户失败");
    }

    @DeleteMapping("/user/{id}")
    public Result removeUserById(@PathVariable("id") Long id) {
        boolean success = sysUserService.removeById(id);
        if (success)
            return ResultFactory.buildSuccessResult("删除用户成功", null);
        else
            return ResultFactory.buildFailResult("删除用户失败");
    }

}
