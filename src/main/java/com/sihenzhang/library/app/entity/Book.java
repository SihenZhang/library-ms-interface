package com.sihenzhang.library.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bookId;

    private String bookName;

    private String bookAuthor;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long catId;

    @TableField(exist = false)
    private String catSymbol;

    @TableField(exist = false)
    private String catName;

    private Integer bookTotal;

    private Integer bookLent;

    @TableLogic
    @TableField(select = false)
    @JsonIgnore
    private LocalDateTime bookDeleted;

}
