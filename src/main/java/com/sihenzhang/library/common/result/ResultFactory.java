package com.sihenzhang.library.common.result;

public class ResultFactory {

    public static Result buildSuccessResult(String message, Object data) {
        return buildResult(ResultCode.OK, message, data);
    }

    public static Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.getCode(), message, data);
    }

    public static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }

}
