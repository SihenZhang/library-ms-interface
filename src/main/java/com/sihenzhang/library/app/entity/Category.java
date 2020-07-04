package com.sihenzhang.library.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long catId;

    private String catSymbol;

    private String catName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long catPid;

    private Integer catLevel;

    @TableLogic
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime catDeleted;

    @TableField(exist = false)
    private List<Category> children;

}
