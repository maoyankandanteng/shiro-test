package cn.itcast.shiro.realm;

import cn.itcast.shiro.domain.Permission;
import cn.itcast.shiro.domain.Role;
import cn.itcast.shiro.domain.User;
import cn.itcast.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public void setName(String name) {
        super.setName("CustomRealm");
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取已经认证的安全数据
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取用户的权限信息
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Set<String> roles=new HashSet<>();
        Set<String> perms=new HashSet<>();
        for(Role role:user.getRoles()){
            roles.add(role.getName());
            for(Permission perm:role.getPermissions()){
                perms.add(perm.getCode());
            }
        }
        info.addStringPermissions(perms);
        info.addRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User userByName = userService.findByName(username);
        if(userByName !=null && userByName.getPassword().equals(password)){
            return new SimpleAuthenticationInfo(userByName,password,getName());
        }
        return null;
    }
}
