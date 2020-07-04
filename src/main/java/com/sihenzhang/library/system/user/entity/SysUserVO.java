package com.sihenzhang.library.system.user.entity;

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
public class SysUserVO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;

    private String email;

    private String phone;

    private Boolean status;

}
