package com.shirodemo.class6.service;

import com.shirodemo.class6.PasswordHelper;
import com.shirodemo.class6.dao.UserDao;
import com.shirodemo.class6.dao.UserDaoImpl;
import com.shirodemo.class6.entity.User;

import java.util.Set;

/**
 * UserServiceImpl
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao = new UserDaoImpl();
    private PasswordHelper passwordHelper = new PasswordHelper();
    @Override
    public User createUser(User user) {
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        userDao.correlationRoles(userId,roleIds);
    }

    @Override
    public void uncorrelationRoles(Long userid, Long... roleIds) {
        userDao.uncorrelationRoles(userid,roleIds);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }
}
