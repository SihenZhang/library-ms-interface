package com.sihenzhang.library.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.app.entity.Category;
import com.sihenzhang.library.app.entity.CategoryVO;

public interface CategoryService extends IService<Category> {

    IPage<CategoryVO> getAllCategories(Integer type, Integer current, Integer size);

}
