package com.shirodemo.class6.dao;

import com.shirodemo.class6.entity.Permission;

/**
 * PermissionDao
 */
public interface PermissionDao {

    Permission createPermission(Permission permission);
    void deletePermission(Long permissionId);
}
