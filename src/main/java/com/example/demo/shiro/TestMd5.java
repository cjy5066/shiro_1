package com.example.demo.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

//测试md5加密和加盐  同样的字符串不管执行多少次，得到的都是同一个
public class TestMd5 {
    public static void main(String[] args) {

        //使用md5
        Md5Hash md5Hash = new Md5Hash("cjy");
        String s = md5Hash.toHex();
        System.out.println(s);  //d20c4c13e901f3623646e0f0177ffc28

        //使用md5 + salt 处理  这里的用随机出来的，我这里先写死。默认盐的位置在字符串的后面
        Md5Hash md5Hash1 = new Md5Hash("cjy","yanzhi");
        String s1 = md5Hash1.toHex();
        System.out.println(s1);     //a7aa49cdcb206a2acde73bfc8abb6e64


        //使用md5 + salt + hash散列  这里的1024 代表你对结果散列多少次 越高就越均匀，越安全。通常是1024和2048
        Md5Hash md5Hash2 = new Md5Hash("cjy","yanzhi",1024);
        String s2 = md5Hash2.toHex();
        System.out.println(s2);     //d6108f69ab8cbf0073850c23bbb19456
    }

}
