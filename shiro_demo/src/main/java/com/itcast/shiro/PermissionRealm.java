package com.itcast.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

public class PermissionRealm extends AuthorizingRealm {
    public void setName(String name) {
        super.setName("PermissionRealm");
    }

    //授权:鉴权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println(username);
        List<String> perms=new ArrayList<String>();
        perms.add("user:save");
        perms.add("user:update");
        List<String> roles=new ArrayList<String>();
        roles.add("role1");
        roles.add("role2");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(perms);
        info.addRoles(roles);
        return info;
    }

    //认证:登陆
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证:登陆");
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        if("123456".equals(password)){
            //1.安全数据 2.密码 3.当前realm域的名称
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(username,password,getName());
            return info;
        }else{
            throw new RuntimeException("用户名或密码失败");
        }
    }
}
