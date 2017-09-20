package com.shirodemo.class6.service;

import com.shirodemo.class6.entity.User;

import java.util.Set;

/**
 * UserService
 */
public interface UserService {
    User createUser(User user);
    void changePassword(Long userId,String newPassword);
    void correlationRoles(Long userId,Long... roleIds);
    void uncorrelationRoles(Long userid,Long... roleIds);
    User findByUsername(String username);
    Set<String> findRoles(String username);
    Set<String> findPermissions(String username);
}
