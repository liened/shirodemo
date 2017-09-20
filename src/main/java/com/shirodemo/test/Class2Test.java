package com.shirodemo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 *  class 1: http://jinnianshilongnian.iteye.com/blog/2019547
 */
public class Class2Test {

    @Test
    public void test(){
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:class2/shiro-authenticator-all-success.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
        try {
            //4、登录，即身份验证
            subject.login(token);
        }catch (Exception e){
            //5、身份验证失败
            e.printStackTrace();
        }
        Assert.assertEquals(true,subject.isAuthenticated());////断言用户已经登录
        //6、退出
        subject.logout();
    }

    @Test
    public void testAllSuccessfulStrategy(){
        login("classpath:class2/shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection =  subject.getPrincipals();
        Assert.assertEquals(2,principalCollection.asList().size());
    }

    @Test
    public void testFirstSuccessfulStrategy(){
        login("classpath:class2/shiro-authenticator-first-success.ini");
        Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了Realm验证成功的身份信息
        String s = subject.getPrincipal().toString();
        System.out.println(s);
//        PrincipalCollection principalCollection =  subject.getPreviousPrincipals();
//        Assert.assertEquals(2,principalCollection.asList().size());

    }

    @Test
    public void testAtLeastOneSuccessfulStrategy(){
        login("classpath:class2/shiro-authenticator-atLeastOne-success.ini");
        Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection =  subject.getPreviousPrincipals();
        Assert.assertEquals(2,principalCollection.asList().size());

    }

    @Test
    public void testAtLeastTwoSuccessfulStrategy(){
        login("classpath:class2/shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();
        String s = subject.getPrincipal().toString();
        System.out.println(s);

    }
    private void login(String configFile){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager =  factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
        subject.login(token);
    }
}
