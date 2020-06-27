package com.sihenzhang.library.common.result;

import lombok.Getter;

public enum ResultCode {

    OK(200),
    CREATED(201),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    @Getter
    private final int code;

    ResultCode(final int code) {
        this.code = code;
    }

}
