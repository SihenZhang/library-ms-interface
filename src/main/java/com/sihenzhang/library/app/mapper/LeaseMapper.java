package com.sihenzhang.library.app.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sihenzhang.library.app.entity.Lease;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LeaseMapper extends BaseMapper<Lease> {

    @Select("SELECT lease_id,lease.stu_id,stu_num,stu_name,lease.book_id,book_name,lease_date,return_date FROM lease " +
            "LEFT JOIN student ON lease.stu_id=student.stu_id LEFT JOIN book ON lease.book_id=book.book_id ${ew.customSqlSegment}")
    IPage<Lease> selectPageVO(Page<Lease> page, @Param(Constants.WRAPPER) Wrapper<Lease> wrapper);

}
