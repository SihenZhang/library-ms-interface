package com.sihenzhang.library.system.user.entity;

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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 255738425073141938L;

    @TableId
    private Long userId;

    private String username;

    private String password;

    private String salt;

    @TableLogic
    private Integer status;

}
