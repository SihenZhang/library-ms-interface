package com.sihenzhang.library.app.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.app.entity.Category;

public interface CategoryService extends IService<Category> {

    Dict getAllCategories(Integer type, Integer current, Integer size);

}
