package com.shirodemo.class6.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Class6:Realm及其相关
 *  http://jinnianshilongnian.iteye.com/blog/2022468
 */
public class ServiceTest extends BaseTest{

    @Test
    public void testUserRolePermissionRelation(){
        //zhang
        Set<String> roles = userService.findRoles(u1.getUsername());
        System.out.println("zhang roles:"+roles);
        Assert.assertEquals(1,roles.size());
        Assert.assertTrue(roles.contains(r1.getRole()));

        Set<String> permissions = userService.findPermissions(u1.getUsername());
        System.out.println("zhang permissions:"+permissions);
        Assert.assertEquals(3,permissions.size());
        Assert.assertTrue(permissions.contains(p3.getPermission()));

        //li
        roles = userService.findRoles(u2.getUsername());
        System.out.println("li roles:"+roles);
        Assert.assertEquals(0,roles.size());
        permissions = userService.findPermissions(u2.getUsername());
        System.out.println("li permissions:"+permissions);
        Assert.assertEquals(0,permissions.size());

        //uncorrelation admin-menu:update
        roleService.uncorrelationPermissions(r1.getId(),p3.getId());
        permissions =userService.findPermissions(u1.getUsername());
        System.out.println("after uncorrelation zhang admin-menu:update:"+permissions);
        Assert.assertEquals(2,permissions.size());
        Assert.assertFalse(permissions.contains(p3.getPermission()));

        //delete a permission
        permissionService.deletePermission(p2.getId());
        permissions = userService.findPermissions(u1.getUsername());
        System.out.println("after delete a permission:"+permissions);
        Assert.assertEquals(1,permissions.size());

        //uncorrelation zhang-admin
        userService.uncorrelationRoles(u1.getId(),r1.getId());
        roles = userService.findRoles(u1.getUsername());
        System.out.println("after uncorrelation zhang-admin:"+roles);
        Assert.assertEquals(0,roles.size());
    }
}
