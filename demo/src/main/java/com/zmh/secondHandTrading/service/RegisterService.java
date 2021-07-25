package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.model.RegisterModel;
import org.springframework.stereotype.Service;

/**
 * @author zmh
 * @title: RegisterService
 * @projectName demo
 * @description: TODO
 * @date 2021/7/25 13:31
 */

public interface RegisterService {
    public int register(RegisterModel registerModel);
}
