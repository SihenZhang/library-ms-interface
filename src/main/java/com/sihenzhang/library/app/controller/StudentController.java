package com.sihenzhang.library.app.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.app.entity.Student;
import com.sihenzhang.library.app.service.StudentService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Result list(@RequestParam(required = false) Integer queryType, @RequestParam(required = false) String query, @RequestParam Integer current, @RequestParam Integer size) {
        try {
            var page = new Page<Student>(current, size);
            IPage<Student> resultPage;
            if (queryType == null || StrUtil.isBlank(query))
                resultPage = studentService.page(page);
            else {
                switch (queryType) {
                    case 0:
                        resultPage = studentService.page(page, Wrappers.<Student>lambdaQuery().like(Student::getStuName, query));
                        break;
                    case 1:
                        resultPage = studentService.page(page, Wrappers.<Student>lambdaQuery().like(Student::getStuNum, query));
                        break;
                    case 2:
                        resultPage = studentService.page(page, Wrappers.<Student>lambdaQuery().like(Student::getStuDept, query));
                        break;
                    default:
                        resultPage = studentService.page(page);
                }
            }
            var data = Dict.create()
                    .set("total", resultPage.getTotal())
                    .set("current", resultPage.getCurrent())
                    .set("students", resultPage.getRecords());
            return ResultFactory.buildSuccessResult("获取学生列表成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取学生列表失败");
        }
    }

    @PostMapping("/student")
    public Result addStudent(@RequestBody Student student) {
        try {
            var success = studentService.save(student);
            if (success)
                return ResultFactory.buildResult(ResultCode.CREATED, "添加学生成功", null);
            else
                return ResultFactory.buildFailResult("添加学生失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("添加学生失败");
        }
    }

    @GetMapping("/student/{id}")
    public Result getById(@PathVariable Long id) {
        try {
            var student = studentService.getById(id);
            return ResultFactory.buildSuccessResult("查询学生信息成功", student);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("查询学生信息失败");
        }
    }

    @PutMapping("/student/{id}")
    public Result editBook(@PathVariable Long id, @RequestBody Student student) {
        try {
            student.setStuId(id);
            var success = studentService.updateById(student);
            if (success)
                return ResultFactory.buildSuccessResult("编辑学生成功", null);
            else
                return ResultFactory.buildFailResult("编辑学生失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("编辑学生失败");
        }
    }

    @DeleteMapping("/student/{id}")
    public Result deleteBook(@PathVariable Long id) {
        try {
            var success = studentService.removeById(id);
            if (success)
                return ResultFactory.buildSuccessResult("删除学生成功", null);
            else
                return ResultFactory.buildFailResult("删除学生失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("删除学生失败");
        }
    }

}
