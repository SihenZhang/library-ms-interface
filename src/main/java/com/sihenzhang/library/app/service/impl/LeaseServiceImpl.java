package com.sihenzhang.library.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.app.entity.Lease;
import com.sihenzhang.library.app.mapper.LeaseMapper;
import com.sihenzhang.library.app.service.BookService;
import com.sihenzhang.library.app.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeaseServiceImpl extends ServiceImpl<LeaseMapper, Lease> implements LeaseService {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public IPage<Lease> selectLeasePage(Page<Lease> page, QueryWrapper<Lease> queryWrapper) {
        queryWrapper.orderByAsc("lease_date");
        queryWrapper.isNull("lease_deleted");
        return baseMapper.selectPageVO(page, queryWrapper);
    }

    public IPage<Lease> selectLeasePage(Page<Lease> page) {
        return selectLeasePage(page, new QueryWrapper<>());
    }

    @Transactional
    public boolean saveLease(Lease lease) {
        // 如果该学生借了同样一本书且未归还，则抛出异常回滚事务
        if (getOne(Wrappers.<Lease>lambdaQuery().eq(Lease::getStuId, lease.getStuId()).eq(Lease::getBookId, lease.getBookId()).isNull(Lease::getReturnDate), false) != null)
            throw new IllegalArgumentException("该学生已经借过这本书且尚未归还");
        save(lease);
        var book = bookService.getById(lease.getBookId());
        // 如果该书的馆藏数量小于等于已经借出去的数量，即目前可借数量为0，则抛出异常回滚事务
        if (book.getBookTotal() <= book.getBookLent())
            throw new IllegalArgumentException("当前书籍暂无库存可借");
        book.setBookLent(book.getBookLent() + 1);
        bookService.updateById(book);
        return true;
    }

    @Transactional
    public boolean returnBook(Lease lease) {
        updateById(lease);
        var leaseWithFullInfo = getById(lease.getLeaseId());
        var book = bookService.getById(leaseWithFullInfo.getBookId());
        book.setBookLent(book.getBookLent() - 1);
        bookService.updateById(book);
        return true;
    }

    @Transactional
    public boolean deleteLeaseById(Long id) {
        var lease = getById(id);
        // 如果借阅的书尚未归还，则在删除借阅信息前需要先将书归还
        if (lease.getReturnDate() == null) {
            var book = bookService.getById(lease.getBookId());
            book.setBookLent(book.getBookLent() - 1);
            bookService.updateById(book);
        }
        removeById(id);
        return true;
    }

}