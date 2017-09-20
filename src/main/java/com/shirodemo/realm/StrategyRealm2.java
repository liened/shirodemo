package com.shirodemo.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**]
 * StrategyRealm2
 */
public class StrategyRealm2 implements Realm {

    @Override
    public String getName() {
        return "strategyRealm2";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        if (!"wang".equals(username)){
            throw new UnknownAccountException();
        }
        if (!"123".equals(username)){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
