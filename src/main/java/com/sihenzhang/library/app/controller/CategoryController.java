package com.sihenzhang.library.app.controller;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sihenzhang.library.app.entity.CategoryVO;
import com.sihenzhang.library.app.service.CategoryService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public Result list(@RequestParam Integer type, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<CategoryVO> categoryIPage = categoryService.getAllCategories(type, current, size);
        Dict data = Dict.create()
                .set("total", categoryIPage.getTotal())
                .set("current", categoryIPage.getCurrent())
                .set("categories", categoryIPage.getRecords());
        return ResultFactory.buildSuccessResult("获取图书分类成功", data);
    }

}
