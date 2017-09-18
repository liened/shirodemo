package com.shirodemo.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 *  class 1: http://jinnianshilongnian.iteye.com/blog/2019547
 */
public class MyRealm implements Realm {

    //返回一个唯一的Realm名字
    @Override
    public String getName() {
        return "myRealm";
    }

    //判断此Realm是否支持此Token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    //根据token获取认证信息
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        if ("zhang".equals(username)){
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
