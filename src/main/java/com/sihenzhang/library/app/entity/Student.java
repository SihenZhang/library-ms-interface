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
public class Student {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long stuId;

    private String stuNum;

    private String stuName;

    private String stuSex;

    private String stuAge;

    private String stuDept;

    @TableLogic
    @TableField(select = false)
    @JsonIgnore
    private LocalDateTime stuDeleted;

}
