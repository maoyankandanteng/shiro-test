package cn.itcast.shiro;

import cn.itcast.shiro.realm.CustomRealm;
import cn.itcast.shiro.session.CustomSessionManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    public CustomRealm getRealm(){
        return new CustomRealm();
    }

    @Bean
    public SecurityManager getSecurityManager(CustomRealm customRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager(customRealm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        //创建过滤器工厂
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setLoginUrl("/autherror?code=1");
        filterFactoryBean.setUnauthorizedUrl("/autherror?code=2");

        Map<String,String> filterMap=new LinkedHashMap<>();
//        filterMap.put("/user/home","anon");
        //具有某种权限才能访问
//        filterMap.put("/user/home","perms[user-home]");//如果不具备指定权限,跳转到UnauthorizedUrl
        filterMap.put("/user/**","authc");



        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return filterFactoryBean;
    }

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    public RedisManager redisManager(){
        RedisManager redisManager=new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        return redisManager;
    }

    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO sessionDAO=new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    public DefaultWebSessionManager sessionManager(){
        CustomSessionManager sessionManager=new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager=new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }


    //配置shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
