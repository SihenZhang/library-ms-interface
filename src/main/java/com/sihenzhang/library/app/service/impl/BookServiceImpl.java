package com.sihenzhang.library.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.app.entity.Book;
import com.sihenzhang.library.app.mapper.BookMapper;
import com.sihenzhang.library.app.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    public IPage<Book> selectBookPage(Page<Book> page, QueryWrapper<Book> queryWrapper) {
        queryWrapper.isNull("book_deleted");
        return baseMapper.selectPageVO(page, queryWrapper);
    }

    public IPage<Book> selectBookPage(Page<Book> page) {
        return selectBookPage(page, new QueryWrapper<>());
    }

}
