package com.itcast.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

//授权
public class Test02 {
    private SecurityManager securityManager;
    @Before
    public void init(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-test-2.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }
    @Test
    public void testLogin(){
        Subject subject = SecurityUtils.getSubject();
        String username="lisi";
        String password="123456";
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        subject.login(token);

        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user:find"));


    }
}
