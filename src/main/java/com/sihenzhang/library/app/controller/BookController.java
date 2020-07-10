package com.sihenzhang.library.app.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.app.entity.Book;
import com.sihenzhang.library.app.service.BookService;
import com.sihenzhang.library.common.result.Result;
import com.sihenzhang.library.common.result.ResultCode;
import com.sihenzhang.library.common.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Result list(@RequestParam(required = false) Integer queryType, @RequestParam(required = false) String query, @RequestParam Integer current, @RequestParam Integer size) {
        try {
            var page = new Page<Book>(current, size);
            IPage<Book> resultPage;
            if (queryType == null || StrUtil.isBlank(query))
                resultPage = bookService.selectBookPage(page);
            else {
                var queryWrapper = new QueryWrapper<Book>();
                switch (queryType) {
                    case 0:
                        queryWrapper.like("book_name", query);
                        break;
                    case 1:
                        queryWrapper.like("book_author", query);
                        break;
                    case 2:
                        queryWrapper.likeRight("cat_symbol", query);
                        break;
                }
                resultPage = bookService.selectBookPage(page, queryWrapper);
            }
            var data = Dict.create()
                    .set("total", resultPage.getTotal())
                    .set("current", resultPage.getCurrent())
                    .set("books", resultPage.getRecords());
            return ResultFactory.buildSuccessResult("获取书籍列表成功", data);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("获取书籍列表失败");
        }
    }

    @PostMapping("/book")
    public Result addBook(@RequestBody Book book) {
        try {
            var success = bookService.save(book);
            if (success)
                return ResultFactory.buildResult(ResultCode.CREATED, "添加书籍成功", null);
            else
                return ResultFactory.buildFailResult("添加书籍失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("添加书籍失败");
        }
    }

    @GetMapping("/book/{id}")
    public Result getById(@PathVariable Long id) {
        try {
            var book = bookService.getById(id);
            return ResultFactory.buildSuccessResult("查询书籍信息成功", book);
        } catch (Exception e) {
            return ResultFactory.buildFailResult("查询书籍信息失败");
        }
    }

    @PutMapping("/book/{id}")
    public Result editBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            book.setBookId(id);
            var success = bookService.updateById(book);
            if (success)
                return ResultFactory.buildSuccessResult("编辑书籍成功", null);
            else
                return ResultFactory.buildFailResult("编辑书籍失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("编辑书籍失败");
        }
    }

    @DeleteMapping("/book/{id}")
    public Result removeBookById(@PathVariable Long id) {
        try {
            var success = bookService.removeById(id);
            if (success)
                return ResultFactory.buildSuccessResult("删除书籍成功", null);
            else
                return ResultFactory.buildFailResult("删除书籍失败");
        } catch (Exception e) {
            return ResultFactory.buildFailResult("删除书籍失败");
        }
    }

}
