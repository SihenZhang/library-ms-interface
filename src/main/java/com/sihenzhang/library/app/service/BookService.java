package com.sihenzhang.library.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.app.entity.Book;

public interface BookService extends IService<Book> {

    IPage<Book> selectBookPage(Page<Book> page, QueryWrapper<Book> queryWrapper);

    IPage<Book> selectBookPage(Page<Book> page);

}
