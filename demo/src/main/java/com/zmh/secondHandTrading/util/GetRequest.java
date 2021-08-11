package com.zmh.secondHandTrading.util;/**
 * @title: GetRequest
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/11 13:37
 */

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *@ClassName GetRequest
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/11 13:37
 *@Version 1.0
 */
public class GetRequest {
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

}
