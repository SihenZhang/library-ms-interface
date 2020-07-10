package com.sihenzhang.library.app.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.app.entity.Lease;
import com.sihenzhang.library.app.service.LeaseService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaseController {

    private LeaseService leaseService;

    @Autowired
    public void setLeaseService(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping("/leases")
    public Result list(@RequestParam(required = false) Integer queryType, @RequestParam(required = false) String query, @RequestParam Integer current, @RequestParam Integer size) {
        try {
            var page = new Page<Lease>(current, size);
            IPage<Lease> resultPage;
            if (queryType == null || StrUtil.isBlank(query))
                resultPage = leaseService.selectLeasePage(page);
            else {
                var queryWrapper = new QueryWrapper<Lease>();
                switch (queryType) {
                    case 0:
                        queryWrapper.like("stu_num", query);
                        break;
                    case 1:
                        queryWrapper.like("stu_name", query);
                        break;
                    case 2:
                        queryWrapper.like("book_name", query);
                        break;
                }
                resultPage = leaseService.selectLeasePage(page, queryWrapper);
            }
            var data = Dict.create()
                    .set("total", resultPage.getTotal())
                    .set("current", resultPage.getCurrent())
                    .set("leases", resultPage.getRecords());
            return ResultFactory.buildSuccessResult("获取借阅信息列表成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取借阅信息列表失败");
        }
    }

    @PostMapping("/lease")
    public Result addLease(@RequestBody Lease lease) {
        try {
            var success = leaseService.saveLease(lease);
            if (success)
                return ResultFactory.buildResult(ResultCode.CREATED, "添加借阅信息成功", null);
            else
                return ResultFactory.buildFailResult("添加借阅信息失败");
        } catch (IllegalArgumentException e) {
            return ResultFactory.buildFailResult(e.getMessage());
        } catch (Exception e) {
            return ResultFactory.buildFailResult("添加借阅信息失败");
        }
    }

    @PutMapping("/lease/{id}")
    public Result returnBook(@PathVariable Long id, @RequestBody Lease lease) {
        try {
            lease.setLeaseId(id);
            var success = leaseService.returnBook(lease);
            if (success)
                return ResultFactory.buildSuccessResult("归还书籍成功", null);
            else
                return ResultFactory.buildFailResult("归还书籍失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("归还书籍失败");
        }
    }

    @DeleteMapping("/lease/{id}")
    public Result removeLeaseById(@PathVariable Long id) {
        try {
            var success = leaseService.deleteLeaseById(id);
            if (success)
                return ResultFactory.buildSuccessResult("删除借阅信息成功", null);
            else
                return ResultFactory.buildFailResult("删除借阅信息失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("删除借阅信息失败");
        }
    }

}
