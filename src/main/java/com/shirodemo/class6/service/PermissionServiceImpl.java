package com.shirodemo.class6.service;

import com.shirodemo.class6.dao.PermissionDao;
import com.shirodemo.class6.dao.PermissionDaoImpl;
import com.shirodemo.class6.entity.Permission;

/**
 * PermissionServiceImpl
 */
public class PermissionServiceImpl implements PermissionService{

    private PermissionDao permissinDao = new PermissionDaoImpl();

    @Override
    public Permission createPermission(Permission permission) {
        return permissinDao.createPermission(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissinDao.deletePermission(permissionId);
    }
}
