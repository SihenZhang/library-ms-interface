package com.sihenzhang.library.app.controller;

import com.sihenzhang.library.app.entity.Category;
import com.sihenzhang.library.app.service.CategoryService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public Result list(@RequestParam(defaultValue = "3") Integer level, @RequestParam(defaultValue = "1") Integer current, @RequestParam(required = false) Integer size) {
        try {
            var data = categoryService.getAllCategories(level, current, size);
            return ResultFactory.buildSuccessResult("获取分类列表成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取分类列表失败");
        }
    }

    @PostMapping("/category")
    public Result addCategory(@RequestBody Category category) {
        try {
            var success = categoryService.save(category);
            if (success)
                return ResultFactory.buildResult(ResultCode.CREATED, "添加分类成功", null);
            else
                return ResultFactory.buildFailResult("添加分类失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("添加分类失败");
        }
    }

    @GetMapping("/category/{id}")
    public Result getById(@PathVariable Long id) {
        try {
            var category = categoryService.getById(id);
            return ResultFactory.buildSuccessResult("查询分类信息成功", category);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("查询分类信息失败");
        }
    }

    @PutMapping("/category/{id}")
    public Result editCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            category.setCatId(id);
            var success = categoryService.updateById(category);
            if (success)
                return ResultFactory.buildSuccessResult("编辑分类成功", null);
            else
                return ResultFactory.buildFailResult("编辑分类失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("编辑分类失败");
        }
    }

    @DeleteMapping("/category/{id}")
    public Result removeCategoryById(@PathVariable Long id) {
        try {
            var success = categoryService.removeById(id);
            if (success)
                return ResultFactory.buildSuccessResult("删除分类成功", null);
            else
                return ResultFactory.buildFailResult("删除分类失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("删除分类失败");
        }
    }

}
