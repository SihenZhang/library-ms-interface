package com.sihenzhang.library.system.user.controller;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.common.result.Result;
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
    public Result list(@RequestParam Integer current, @RequestParam Integer size) {
        Page<SysUserVO> page = new Page<>(current, size);
        IPage<SysUserVO> userIPage = sysUserService.selectUserPage(page);
        Dict data = Dict.create()
                .set("total", userIPage.getTotal())
                .set("pageNum", userIPage.getCurrent())
                .set("users", userIPage.getRecords());
        return ResultFactory.buildSuccessResult(data);
    }

    @PutMapping("/users/{id}/active/{active}")
    public Result changeActive(@PathVariable("id") Long id, @PathVariable("active") Boolean active) {
        SysUser newUser = SysUser.builder().userId(id).active(active).build();
        sysUserService.updateById(newUser);
        return ResultFactory.buildSuccessResult(null);
    }

}
