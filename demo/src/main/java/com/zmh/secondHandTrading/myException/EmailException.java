package com.zmh.secondHandTrading.myException;/**
 * @title: EmailException
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/24 15:51
 */

import com.zmh.secondHandTrading.myEnum.ResultCode;
import com.zmh.secondHandTrading.util.CommonResult;

import javax.mail.MessagingException;

/**
 *@ClassName EmailException
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/24 15:51
 *@Version 1.0
 */
public class EmailException extends MessagingException {
    public EmailException(){
        super();
    }

    public EmailException(String msg){
        super(msg);
    }

    public CommonResult myPrintStackTrace() {
        return CommonResult.failed(ResultCode.FAILED,"发送邮件失败");
    }
}
