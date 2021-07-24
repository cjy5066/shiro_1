package com.example.demo.Realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

//自定义Realm  将认证/授权数据的来源转为数据库中的实现
public class TestRealm extends AuthorizingRealm {

    //授权  AuthorizingRealm父类中的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证  AuthorizingRealm本类中的
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 在token中获取用户名
        String principal = (String) token.getPrincipal();
        System.out.println(principal);

        //根据身份信息使用数据库查询‘
        if("cjy".equals(principal)){
            /**
             * SimpleAuthenticationInfo类是AuthenticationInfo类的实现
             * 参数1：返回数据中查询的用户名
             * 参数2：返回数据中正确的密码
             *  参数3：提供当前realm的名字
             */
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"123",this.getName());
            return simpleAuthenticationInfo;

        }
        return null;
    }
}
