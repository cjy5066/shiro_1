package com.example.demo.Realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

//这里用md5的方式写Realm  而TestRealm类没有用
public class TestRealmMd5 extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权：===========================");
        String primaryPrincipal =(String) principalCollection.getPrimaryPrincipal();
        System.out.println(primaryPrincipal);
        //根据身份信息 用户名 获取当前用户的角色信息，以及权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //将数据库中查询角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("root");
        //赋值多个权限对象
        simpleAuthorizationInfo.addRole("admin");

        //将数据库中查询权限信息赋值个权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:*");
        simpleAuthorizationInfo.addStringPermission("dep:*");
        return simpleAuthorizationInfo;
    }

    //加密
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        if("cjy".equals(principal)){
            /**
             * SimpleAuthenticationInfo类是AuthenticationInfo类的实现
             * 参数1：返回数据中查询的用户名
             * 参数2：返回数据中正确的密码
             *  参数3：提供当前realm的名字
             */

            //这个没有使用盐
            /*SimpleAuthenticationInfo simplePrincipalCollection= new SimpleAuthenticationInfo(
                    principal,
                    "d20c4c13e901f3623646e0f0177ffc28",
                    this.getName());*/

            //这里使用了盐值
            /*SimpleAuthenticationInfo simplePrincipalCollection= new SimpleAuthenticationInfo(
                    principal,
                    "a7aa49cdcb206a2acde73bfc8abb6e64",
                    ByteSource.Util.bytes("yanzhi"),
                    this.getName());*/

            //这里用的是盐 + hash散列算法
            SimpleAuthenticationInfo simplePrincipalCollection= new SimpleAuthenticationInfo(
                    principal,
                    "d6108f69ab8cbf0073850c23bbb19456",
                    ByteSource.Util.bytes("yanzhi"),
                    this.getName());

            return simplePrincipalCollection;
        }
        return null;
    }
}
