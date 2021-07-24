package com.example.demo.shiro;

import com.example.demo.Realm.TestRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

//使用自定义的Realm进行测试

/**
 * 可以使用数据中的值，不过这里还没有使用数据库还是写死了的
 */
public class TestRealmAuthenticator {
    public static void main(String[] args) {

        //创建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        //设置自定义realm  在TestAuthenticator类中用的是shiro.ini文件中的【是写死了的】
        defaultSecurityManager.setRealm(new TestRealm());

        //将安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();

        //创建token
        UsernamePasswordToken token = new UsernamePasswordToken("cjy","123");

        try{
            subject.login(token);
            System.out.println(subject.isAuthenticated());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
