package com.sihenzhang.library.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lease {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long leaseId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long stuId;

    @TableField(exist = false)
    private String stuNum;

    @TableField(exist = false)
    private String stuName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long bookId;

    @TableField(exist = false)
    private String bookName;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate leaseDate;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate returnDate;

    @TableLogic
    @TableField(select = false)
    @JsonIgnore
    private LocalDateTime leaseDeleted;

}
