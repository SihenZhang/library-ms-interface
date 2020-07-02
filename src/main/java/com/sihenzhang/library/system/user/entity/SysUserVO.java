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
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = -2220187565374171595L;

    private Long userId;

    private String username;

    private String email;

    private String phone;

    private Boolean status;

}
