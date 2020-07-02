package com.sihenzhang.library.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {

    private static final long serialVersionUID = -1179259928358785674L;

    @TableId
    private Long catId;

    private String catSymbol;

    private String catName;

    private Long catPid;

    private Integer catLevel;

    @TableLogic(value = "false", delval = "true")
    private Boolean catDeleted;

}
