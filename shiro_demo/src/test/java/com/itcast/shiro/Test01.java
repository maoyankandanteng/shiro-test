package com.itcast.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class Test01 {
    /**
     * 测试用户认证
     *  认证：用户登陆
     *          1、根据配置文件创建SecurityManagerFactory
     *          2、通过工厂获取SecurityManager
     *          3、将SecurityManager绑定到当前运行环境
     *          4.构造Subject
     *          5、构造shiro登录的数据
     *          6、主题登陆
     */
    @Test
    public void testLogin(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-test-1.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        String username="zhangsan";
        String password="123456";
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        subject.login(token);
        System.out.println("是否登陆成功："+subject.isAuthenticated());
        System.out.println(subject.getPrincipal());

    }
}
