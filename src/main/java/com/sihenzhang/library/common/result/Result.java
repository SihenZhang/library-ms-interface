package com.sihenzhang.library.common.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Result implements Serializable {

    private static final long serialVersionUID = -1050577320533946077L;

    private int status;
    private String message;
    private Object data;

    public Result(int status, String message) {
        this(status, message, null);
    }

}
