package com.sihenzhang.library.app.controller;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sihenzhang.library.app.entity.Category;
import com.sihenzhang.library.app.entity.CategoryVO;
import com.sihenzhang.library.app.service.CategoryService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public Result list(@RequestParam(defaultValue = "5") Integer type, @RequestParam(defaultValue = "1") Integer current, @RequestParam(required = false) Integer size) {
        try {
            var data = categoryService.getAllCategories(type, current, size);
            return ResultFactory.buildSuccessResult("获取分类成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取分类失败");
        }
    }

    @PostMapping("/category")
    public Result addCategory(@RequestBody Category category) {
        boolean success = categoryService.save(category);
        if (success)
            return ResultFactory.buildResult(ResultCode.CREATED, "添加分类成功", null);
        else
            return ResultFactory.buildFailResult("添加分类失败");
    }

    @GetMapping("/category/{id}")
    public Result getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResultFactory.buildSuccessResult("查询分类信息成功", category);
    }

    @PutMapping("/category/{id}")
    public Result editCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setCatId(id);
        boolean success = categoryService.updateById(category);
        if (success)
            return ResultFactory.buildSuccessResult("编辑分类成功", null);
        else
            return ResultFactory.buildFailResult("编辑分类失败");
    }

    @DeleteMapping("/category/{id}")
    public Result deleteCategory(@PathVariable Long id) {
        boolean success = categoryService.removeById(id);
        if (success)
            return ResultFactory.buildSuccessResult("删除分类成功", null);
        else
            return ResultFactory.buildFailResult("删除分类失败");
    }

}
