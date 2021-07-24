package com.example.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

//Authenticator  对用户身份进行认证的
public class TestAuthenticator {
    public static void main(String[] args) {

        //1.创建secutityManager   这里的DefaultSecurityManager 是创建secutityManager的之类
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        //3.Securityutils 给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.关键对象subject主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("cjy","12");
        try{
            System.out.println("认证状态："+subject.isAuthenticated());
            subject.login(token);   //用户认证，如果认证通过没有任何异常
            System.out.println("认证状态："+subject.isAuthenticated());
        }catch (UnknownAccountException e){
            throw new UnknownAccountException("用户名不存在");
        }catch(IncorrectCredentialsException e){
            throw new IncorrectCredentialsException("用户密码错误");
        }

    }
}
