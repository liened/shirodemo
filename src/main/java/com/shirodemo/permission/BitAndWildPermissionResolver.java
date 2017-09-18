package com.shirodemo.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 *  class3:http://jinnianshilongnian.iteye.com/blog/2020017
 * BitAndWildPermissionResolver实现了PermissionResolver接口，并根据权限字符串是否以“+”开头来解析权限字符串为BitPermission或WildcardPermission。
 */
public class BitAndWildPermissionResolver implements PermissionResolver{
    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")){
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
