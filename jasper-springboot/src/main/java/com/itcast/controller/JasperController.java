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
import java.util.HashMap;
import java.util.Map;

@RestController
public class JasperController {

    @GetMapping("/testJasper")
    public void createPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource=new ClassPathResource("template/test.jasper");
        FileInputStream fis=new FileInputStream(resource.getFile());
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            /**
             * fis 文件输入流
             * hashMap 向模版输入的参数
             * jasperDataSource :数据源(connection,javaBean,Map)
             */
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis, new HashMap<>(), new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

    }
    @GetMapping("/testParam")
    public void testParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource=new ClassPathResource("template/Blank_A4.jasper");
        FileInputStream fis=new FileInputStream(resource.getFile());
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("username","白海峰");
            param.put("mobile","17603286836");
            param.put("dept","dev");
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis,param, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

    }
}
