package com.zmh.secondHandTrading.entity.model;/**
 * @title: CertificationModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/26 14:17
 */

import com.zmh.secondHandTrading.util.Cid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *@ClassName CertificationModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/26 14:17
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificationModel {
    // 认证信息moddel
    @NotNull(message = "姓名不能为空")
    @Length(max = 6, min = 2,message = "姓名长度限制2~6字符")
    private String realName;

    @NotEmpty(message = "身份证号不能为空")
    @Cid
    private String idCard;
    @NotEmpty(message = "学号不能为空")
    private String studentId;
}
