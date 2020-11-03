package com.itcast;

import com.baidu.aip.util.Base64Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.xml.internal.messaging.saaj.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QrCodeTest {
  /*  //保存到本地图片
  public static void main(String[] args) throws WriterException, IOException {
        String content="http://www.itcast.cn";
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bt = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
        Path path = new File("E:\\Cache\\BaiduNetdiskDownload\\前后端分离微服务管理系统项目实战人资SaaS-HRM项目\\09-刷脸登录\\资源\\照片\\test.png").toPath();
        MatrixToImageWriter.writeToPath(bt,"png",path);


    } */
  //dataURL
    public static void main(String[] args) throws WriterException, IOException {
        String content="http://www.itcast.cn";
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bt = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream os =new ByteArrayOutputStream();
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bt);
        ImageIO.write(bufferedImage,"png",os);
        String encode = Base64Util.encode(os.toByteArray());
        System.out.println(new String("data:image/png;base64,"+encode));

    }
}
