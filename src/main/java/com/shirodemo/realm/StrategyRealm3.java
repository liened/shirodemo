package com.shirodemo.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class StrategyRealm3 implements Realm{
    @Override
    public String getName() {
        return "strategyRealm3";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        if (!"zhang".equals(username)){
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username+"@163.com",password,getName());
    }
}
