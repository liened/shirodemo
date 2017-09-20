package com.shirodemo.class6.dao;

import com.shirodemo.class6.entity.User;

import java.util.Set;

/**
 * UserDao
 */
public interface UserDao {
    User createUser(User user);
    void updateUser(User user);
    void deleteUser(Long userId);

    void correlationRoles(Long userId, Long... roleIds);
    void uncorrelationRoles(Long userid, Long... roleIds);

    User findOne(Long userId);
    User findByUsername(String username);

    Set<String> findRoles(String username);
    Set<String> findPermissions(String username);
}
