package com.shirodemo.class6.dao;

import com.shirodemo.class6.entity.Role;

/**
 * RoleDao
 */
public interface RoleDao {
    Role createRole(Role role);
    void deleteRole(Long roleId);
    //添加角色-权限之间关系
    void correlationPermission(Long roleId,Long... permissionIds);
    //移除角色-权限之间关系
    void uncorrelationPermissions(Long roleId,Long... permissionIds);
}
