package com.sihenzhang.library.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private Boolean status;

}
