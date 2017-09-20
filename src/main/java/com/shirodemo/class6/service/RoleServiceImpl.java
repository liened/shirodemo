package com.shirodemo.class6.service;

import com.shirodemo.class6.dao.RoleDao;
import com.shirodemo.class6.dao.RoleDaoImpl;
import com.shirodemo.class6.entity.Role;

/**
 * RoleServiceImpl
 */
public class RoleServiceImpl implements RoleService{
    private RoleDao roleDao = new RoleDaoImpl();

    @Override
    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public void correlationPermission(Long roleId, Long... permissionIds) {
        roleDao.correlationPermission(roleId,permissionIds);
    }

    @Override
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        roleDao.uncorrelationPermissions(roleId,permissionIds);
    }
}
