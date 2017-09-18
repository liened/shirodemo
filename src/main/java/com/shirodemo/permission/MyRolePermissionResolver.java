package com.shirodemo.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * class3: http://jinnianshilongnian.iteye.com/blog/2020017
 * 此处的实现很简单，如果用户拥有role1，那么就返回一个“menu:*”的权限。
 */
public class MyRolePermissionResolver implements RolePermissionResolver{
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)){
            return Arrays.asList((Permission)new WildcardPermission("menu:*"));
        }
        return null;
    }
}
