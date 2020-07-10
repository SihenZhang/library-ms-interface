package com.sihenzhang.library.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sihenzhang.library.app.entity.Lease;

public interface LeaseService extends IService<Lease> {

    IPage<Lease> selectLeasePage(Page<Lease> page, QueryWrapper<Lease> queryWrapper);

    IPage<Lease> selectLeasePage(Page<Lease> page);

    boolean saveLease(Lease lease);

    boolean returnBook(Lease lease);

    boolean deleteLeaseById(Long id);

}
