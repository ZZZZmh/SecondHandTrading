package com.zmh.secondHandTrading.util;/**
 * @title: GetSession
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/11 13:37
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *@ClassName GetSession
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/11 13:37
 *@Version 1.0
 */
public class GetSession {
    public static HttpSession getSession(){
        HttpServletRequest request = GetRequest.getRequest();
        HttpSession session = request.getSession();
        return session;
    }

}
