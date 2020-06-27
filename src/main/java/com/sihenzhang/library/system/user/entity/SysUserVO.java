package com.sihenzhang.library.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = -5629713969279486975L;

    @TableId
    private Long userId;

    private String username;

    private String email;

    private String phone;

    private Boolean active;

}
