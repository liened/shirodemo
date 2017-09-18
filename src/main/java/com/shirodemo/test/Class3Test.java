package com.shirodemo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import sun.security.ssl.SunJSSE;

import java.util.Arrays;


/**
 * http://jinnianshilongnian.iteye.com/blog/2020017
 */
public class Class3Test {

    @Test
    public void testRole(){
        login("classpath:class3/shiro-role.ini","zhang","123");
        //判断拥有角色:role1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色:role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1","role2")));
        //判断拥有角色:role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1","role2","role3"));
        Assert.assertEquals(true,result[0]);
        Assert.assertEquals(true,result[1]);
        Assert.assertEquals(false,result[2]);
    }

    @Test
    public void testCheckRole(){
        login("classpath:class3/shiro-role.ini","zhang","123");
        //断言拥有角色: role1
        subject().checkRole("role1");
        //断言拥有角色: role1 and role3 失败抛出异常
        subject().checkRoles("role1","role3");
    }

    @Test
    public void testisPermission(){
        login("classpath:class3/shiro-permission.ini","zhang","123");
        //判断用于权限:user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        Assert.assertTrue(subject().isPermittedAll("user:udpate","user:delete"));
        Assert.assertTrue(subject().isPermitted("user:view"));
    }

    @Test
    public void testCheckPermission(){
        login("classpath:class3/shiro-permission.ini","zhang","123");
        subject().checkPermission("user:create");
        subject().checkPermissions("user:create","user:view");
    }

    @Test
    public void testMyAuthorizer(){
        login("classpath:class3/shiro-authorizer.ini","zhang","123");
        //判断拥有权限: user:create
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));//新增
        Assert.assertTrue(subject().isPermitted("+user1+8"));//查看
        Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看
        Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限
        Assert.assertTrue(subject().isPermitted("menu:view"));////通过MyRolePermissionResolver解析得到的权限
    }

    private void login(String configFile,String username,String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token =  new UsernamePasswordToken(username,password);
        subject.login(token);
    }

    private Subject subject(){
        return SecurityUtils.getSubject();
    }
}

