package com.itcast.controller;

import com.itcast.domain.User;
import com.itcast.domain.UserCount;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JasperController4 {

    /**
     * 基于javaBean数据源的形式
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/testPie")
    public void testPie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource=new ClassPathResource("template/testPie.jasper");
        FileInputStream fis=new FileInputStream(resource.getFile());
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            /**
             * fis 文件输入流
             * hashMap 向模版输入的参数
             * jasperDataSource :数据源(connection,javaBean,Map)
             */
            Map<String,Object> map=new HashMap<>();
            List<UserCount> list=new ArrayList<>();
            list.add(new UserCount("开发部",10L));
            list.add(new UserCount("财务部",5L));
            list.add(new UserCount("人事部",8L));
            list.add(new UserCount("市场部",12L));
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
