package com.itcast.controller;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JasperController1 {

    /**
     * 基于jdbc数据源的形式
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/testConn")
    public void testConn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource=new ClassPathResource("template/testConn.jasper");
        FileInputStream fis=new FileInputStream(resource.getFile());
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            /**
             * fis 文件输入流
             * hashMap 向模版输入的参数
             * jasperDataSource :数据源(connection,javaBean,Map)
             */
            Map<String,Object> map=new HashMap<>();
            map.put("cid","1");
            Connection connection = getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis,map , connection);
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ihrm", "root", "root");
        return connection;
    }

}
