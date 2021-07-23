package com.zmh.secondHandTrading.until;

/**
 * 公共数据返回体
 */

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResult<T> {
    private long code;
    private String msg;
    private T data;

    protected CommonResult(long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 这是一个方法
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMsg(),
                data);
    }

    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<>(
                ResultCode.SUCCESS.getCode(),
                msg,
                data);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(
                errorCode.getCode(),
                errorCode.getMsg(),
                null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode, String msg) {
        return new CommonResult<T>(
                errorCode.getCode(),
                msg,
                null);
    }
    public static <T> CommonResult<T> failed(int errorCode, String msg) {
        return new CommonResult<T>(
                errorCode,
                msg,
                null);
    }

    public static <T> CommonResult<T> unauthorized(String msg) {
        return new CommonResult<T>(
                ResultCode.UNAUTHORIZED.getCode(),
                msg,
                null);
    }

}
