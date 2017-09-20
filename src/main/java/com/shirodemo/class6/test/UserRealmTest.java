package com.shirodemo.class6.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * UserRealmTest
 */
public class UserRealmTest extends BaseTest{

    @Test
    public void testLoginSuccess(){
        login("classpath:class6/shiro.ini",u1.getUsername(),password);
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUnknownUsername(){
        login("classpath:class6/shiro.ini",u1.getUsername()+"1",password);
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFailWithErrorPassword(){
        login("classpath:class6/shiro.ini",u1.getUsername(),password+"1");
    }

    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithLocked(){
        login("classpath:class6/shiro.ini",u4.getUsername(),password);
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithLimitRetryCount(){
        for (int i= 1;i<=5;i++){
            try {
                login("classpath:class6/shiro.ini",u3.getUsername(),password+"1");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        login("classpath:class6/shiro.ini",u3.getUsername(),password+"1");
    }

    @Test
    public void testHasRole(){
        login("classpath:class6/shiro.ini",u1.getUsername(),password);
        Assert.assertTrue(SecurityUtils.getSubject().hasRole("admin"));
    }

    @Test
    public void testNoRole(){
        login("classpath:class6/shiro.ini",u2.getUsername(),password);
        Assert.assertFalse(SecurityUtils.getSubject().hasRole("admin"));
    }

    @Test
    public void testHasPermission(){
        login("classpath:class6/shiro.ini",u1.getUsername(),password);
        Assert.assertTrue(SecurityUtils.getSubject().isPermittedAll("user:create","menu:create"));
    }

    @Test
    public void testNoPermission(){
        login("classpath:class6/shiro.ini",u2.getUsername(),password);
        Assert.assertFalse(SecurityUtils.getSubject().isPermittedAll("user:create"));
    }


    public void login(String configFile,String username,String passwod){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityUtils.setSecurityManager(factory.getInstance());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,passwod);
        subject.login(token);
    }
}
