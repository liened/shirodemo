package com.shirodemo.class6.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码多次Matcher
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count +1
        Element element = passwordRetryCache.get(username);
        if (element == null){
            element = new Element(username,new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        if (retryCount.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException("请求次数超过5次");
        }

        boolean matches = super.doCredentialsMatch(token,info);
        if (matches){
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
