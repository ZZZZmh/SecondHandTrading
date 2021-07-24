package com.zmh.secondHandTrading.util;/**
 * @title: ImageUtil
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/24 17:20
 */

/**
 *@ClassName ImageUtil
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/24 17:20
 *@Version 1.0
 */
import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ImageUtil {
    public static String upload(MultipartFile file){
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = "h0-z4g_5rx2d0yoRZ7itl8LduRW5nAmaByzxrbyE";      //AccessKey的值
        String secretKey = "****************************************";      //SecretKey的值
        String bucket = "zmh-s-h-t";                                        //存储空间名

        //在七牛云中图片的命名:UUID格式生成图片名
        String key = IdUtil.randomUUID()+".png";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        //图片转换为数据流
        InputStream inputStream = null;
        try {
            byte [] byteArr=file.getBytes();
            inputStream = new ByteArrayInputStream(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //通过数据流上传文件
        try {
            Response response = uploadManager.put(inputStream,key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        //获取图片URL
        String fileName = key;
        String domainOfBucket = "http://quwabxysw.hn-bkt.clouddn.com";
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        //返回URL
        return  finalUrl;
    }
}
