package com.sihenzhang.library.app.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.app.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BookMapper extends BaseMapper<Book> {

    @Select("SELECT book_id,book_name,book_author,book.cat_id,cat_symbol,cat_name,book_total,book_lent FROM book " +
            "LEFT JOIN category ON book.cat_id=category.cat_id ${ew.customSqlSegment}")
    IPage<Book> selectPageVO(Page<Book> page, @Param(Constants.WRAPPER) Wrapper<Book> wrapper);

}
