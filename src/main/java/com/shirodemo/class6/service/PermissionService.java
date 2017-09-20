package com.shirodemo.class6.service;

import com.shirodemo.class6.entity.Permission;

/**
 * PermissionService
 */
public interface PermissionService {

    Permission createPermission(Permission permission);
    void deletePermission(Long permissionId);
}
