package com.sihenzhang.library.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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

    private static final long serialVersionUID = -5321778646163860005L;

    @TableId
    private Long userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private Boolean status;

}
