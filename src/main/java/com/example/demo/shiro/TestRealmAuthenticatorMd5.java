package com.example.demo.shiro;

import com.example.demo.Realm.TestRealmMd5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

//这里使用md5的方式认证   而TestRealmAuthenticator类没有
public class TestRealmAuthenticatorMd5 {
    public static void main(String[] args) {
        //创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        TestRealmMd5 testRealmMd5 = new TestRealmMd5();

                //设置realm使用hash凭证匹配器
                HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
                //使用算法
                hashedCredentialsMatcher.setHashAlgorithmName("md5");
                //散列次数  如果TestRealmMd5类中没有使用盐 + hash散列算法 这一步可以注释
                hashedCredentialsMatcher.setHashIterations(1024);
                testRealmMd5.setCredentialsMatcher(hashedCredentialsMatcher);

        //注入realm
        defaultSecurityManager.setRealm(testRealmMd5);

        //将安全管理器注入安全工具
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();

        //认证
        UsernamePasswordToken token = new UsernamePasswordToken("cjy", "cjy");
        try{
        subject.login(token);
            System.out.println(subject.isAuthenticated());
        }catch (UnknownAccountException e){
            throw  new UnknownAccountException("用户名错误");
        }catch (IncorrectCredentialsException e){
            throw new IncorrectCredentialsException("密码错误");
        }


        //认证用户进行授权
        if(subject.isAuthenticated()){

            //基于 单 角色权限控制
            System.out.println(subject.hasRole("root"));

            //基于 多 角色控制         这儿有两个执行两次
            System.out.println( subject.hasAllRoles(Arrays.asList("root","admin")));

            //是否具有其中一个角色       这儿有三个执行三次
            boolean[] booleans = subject.hasRoles(Arrays.asList("root", "admin", "users"));
            for (boolean a:booleans){
                System.out.println(a);
            }

            System.out.println("0000000000000000000000000000");

            //基于权限字符串的访问控制  资源标识符：操作：资源类型
            System.out.println("权限："+subject.isPermitted("user:*:*"));
            System.out.println("权限："+subject.isPermitted("dep:find:abc"));

            //是否具有这些权限
            boolean[] permitted = subject.isPermitted("user:*:abc", "dep:*:abc");
            for(boolean b:permitted){
                System.out.println(b);
            }
            System.out.println("-----------------------");
            //同时具有哪些权限
            boolean permittedAll = subject.isPermittedAll("user:*:abc", "dep:find");
            System.out.println(permittedAll);


        }
    }
}
