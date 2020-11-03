package com.itcast.controller;

import com.itcast.domain.User;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JasperController2 {

    /**
     * 基于javaBean数据源的形式
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/testBean")
    public void testBean(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource=new ClassPathResource("template/testBean.jasper");
        FileInputStream fis=new FileInputStream(resource.getFile());
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            /**
             * fis 文件输入流
             * hashMap 向模版输入的参数
             * jasperDataSource :数据源(connection,javaBean,Map)
             */
            Map<String,Object> map=new HashMap<>();
            List<User> list=new ArrayList<>();
            list.add(new User("1","白海峰","176********","开发部"));
            list.add(new User("2","白海峰","176********","开发部"));
            list.add(new User("3","白海峰","176********","开发部"));
            list.add(new User("4","白海峰","176********","开发部"));
            list.add(new User("5","白海峰","176********","开发部"));
            list.add(new User("6","白海峰","176********","开发部"));
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis,map , jrBeanCollectionDataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

    }

}
