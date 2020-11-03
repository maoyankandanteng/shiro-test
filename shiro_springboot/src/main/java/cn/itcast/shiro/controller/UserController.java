package cn.itcast.shiro.controller;

import cn.itcast.shiro.domain.User;
import cn.itcast.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 使用过滤器：如果不具备指定权限,跳转到UnauthorizedUrl
     * 使用注解：抛出异常
     * @return
     */
    //主页面
    @RequiresPermissions("user-home")
//    @RequiresRoles()
    @RequestMapping(value = "/user/home",method = RequestMethod.GET)
    public String home() {
        return "用户主页面";
    }

    //添加
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String add() {
        return "添加用户成功";
    }
	
    //查询
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String find() {
        return "查询用户成功";
    }
	
    //更新
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String update(String id) {
        return "更新用户成功";
    }
	
    //删除
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public String delete() {
        return "删除用户成功";
    }
	
	//用户登录
	@RequestMapping(value="/login")
    public String login(String username,String password) {
        try{
            //密码加密(密码，盐：/*加密的混淆字符串*/
//            password= new Md5Hash(password,username,3).toString();
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();
            String sessionId = (String) subject.getSession().getId();
            subject.login(token);
            return "登录成功:"+sessionId;
        }catch (Exception e){
            return "用户名或密码错误";
        }
    }
    @RequestMapping(value="/autherror")
    public String autherror(int code) {
        return code==1?"未登录":"未授权";
    }

    @RequestMapping(value="/show")
    public String show(HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement().toString();
            Object value = session.getAttribute(name);
            System.out.println("["+name+"]="+value+"/n");

        }
        return "查看session成功";
    }
}
