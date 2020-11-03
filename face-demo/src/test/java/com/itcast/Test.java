package com.itcast;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Test {
    private AipFace client;

    @org.junit.Test
    public void testFaceRegister() throws IOException {
        HashMap<String,String> map=new HashMap<String, String>();
        map.put("quality_control","NORMAL");
        map.put("liveness_control","LOW");
        String path="E:\\Cache\\BaiduNetdiskDownload\\前后端分离微服务管理系统项目实战人资SaaS-HRM项目\\09-刷脸登录\\资源\\照片\\001.png";

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);
        //调用api方法完成人脸注册
        /**
         * url 或 Base64字符串
         * URL   Base64
         * 组ID
         * 用户ID
         * HashMap中的基本参数设置
         */
        JSONObject res = client.addUser(encode, "BASE64", "itcast", "1000", map);
        System.out.println(res);
    }



    @Before
    public void init(){
        client=new AipFace("22859002","vUp1cbDoRZ3SikGq4NWzncBU","r2LBlnxfsy45QfOHhKCUdy1RA5OKuGyz");
    }

    /**
     * 人脸检测：判断图片中是否具有面部信息
     */
    @org.junit.Test
    public void testContainFace() throws IOException {
        String path="E:\\Cache\\BaiduNetdiskDownload\\前后端分离微服务管理系统项目实战人资SaaS-HRM项目\\09-刷脸登录\\资源\\照片\\001.png";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);

        JSONObject res = client.detect(encode, "BASE64", null);
        System.out.println(res.toString(2));

    }

    /**
     * 人脸搜索
     * score:相似度评分：80分以上可以认为是同一个人
     */
    @org.junit.Test
    public void testFaceSearch() throws IOException {
        String path="E:\\Cache\\BaiduNetdiskDownload\\前后端分离微服务管理系统项目实战人资SaaS-HRM项目\\09-刷脸登录\\资源\\照片\\003.png";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);

        JSONObject search = client.search(encode, "BASE64", "itcast", null);
        System.out.println(search.toString(2));
    }

    /**
     * 人脸更新
     */
    @org.junit.Test
    public  void testFaceUpdate() throws IOException {
        String path="E:\\Cache\\BaiduNetdiskDownload\\前后端分离微服务管理系统项目实战人资SaaS-HRM项目\\09-刷脸登录\\资源\\照片\\001.png";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);

        HashMap<String,String> map=new HashMap<String, String>();
        map.put("quality_control","NORMAL");
        map.put("liveness_control","LOW");

        JSONObject res = client.updateUser(encode, "BASE64", "itcast", "1000", map);

    }




}
