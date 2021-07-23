package com.zmh.secondHandTrading.until;

public enum ResultCode implements com.zmh.secondHandTrading.until.IErrorCode {

    //通用结果
    OTHERS(100,"其他异常"),
    SUCCESS(200, "操作成功"),
    FAILED(500, "服务器执行失败异常"),
    VALIDATE_FAILED(400, "客户端请求的语法错误，服务器无法理解"),
    UNAUTHORIZED(401, "请求要求用户的身份认证，用户无token"),
    FORBIDDEN(403, "没有相关权限"),

    //用户校验异常
    TOKEN_NOT_PASS(4001,"参数异常:未传递token"),
    TOKEN_NOT_EXISTENT(4002,"token解析失败：可能已过期"),

    //用户异常
    USER_NOT_EXISTENT(4103,"用户不存在"),



    END(65986,"结束");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
