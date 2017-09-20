package com.shirodemo.class6.test;

import com.shirodemo.class6.JdbcTemplateUtils;
import com.shirodemo.class6.entity.Permission;
import com.shirodemo.class6.entity.Role;
import com.shirodemo.class6.entity.User;
import com.shirodemo.class6.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;

/**
 *  BaseTest： 准备测试数据
 */
public class BaseTest {

    protected PermissionService permissionService = new PermissionServiceImpl();
    protected RoleService roleService = new RoleServiceImpl();
    protected UserService userService = new UserServiceImpl();

    protected String password = "123";

    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;

    @Before
    public void setUp(){
        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users");
        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles");
        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_permissions");
        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users_roles");
        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles_permissions");

        //add permission
        p1 = new Permission("user:create","用户新增",Boolean.TRUE);
        p2 = new Permission("user:update","用户修改",Boolean.TRUE);
        p3 = new Permission("menu:create","菜单新增",Boolean.TRUE);
        permissionService.createPermission(p1);
        permissionService.createPermission(p2);
        permissionService.createPermission(p3);

        r1 = new Role("admin","管理员",Boolean.TRUE);
        r2 = new Role("user","用户管理员",Boolean.TRUE);
        roleService.createRole(r1);
        roleService.createRole(r2);

        roleService.correlationPermission(r1.getId(),p1.getId());
        roleService.correlationPermission(r1.getId(),p2.getId());
        roleService.correlationPermission(r1.getId(),p3.getId());

        roleService.correlationPermission(r2.getId(),p1.getId());
        roleService.correlationPermission(r2.getId(),p2.getId());

        u1 = new User("zhang",password);
        u2 = new User("li",password);
        u3 = new User("wu",password);
        u4 = new User("wang",password);
        u4.setLocked(Boolean.TRUE);

        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);

        userService.correlationRoles(u1.getId(),r1.getId());
    }

    @After
    public void tearDown() throws Exception{
        ThreadContext.unbindSubject();//退出时解除绑定subject到线程，否则对下次测试造成影响
    }

    /**
     * 通过login登录，如果登录失败将抛出相应的AuthenticationException，
     *  如果登录成功调用isAuthenticated就会返回true，即已经通过身份验证；
     *   如果isRemembered返回true，表示是通过记住我功能登录的而不是调用login方法登录的。
     *   isAuthenticated/isRemembered是互斥的，即如果其中一个返回true，另一个返回false。
     *   hasRole*进行角色验证，验证后返回true/false；而checkRole*验证失败时抛出AuthorizationException异常。
     *   isPermitted*进行权限验证，验证后返回true/false；而checkPermission*验证失败时抛出AuthorizationException。
     * @param configFile
     * @param username
     * @param password
     */
    protected void login(String configFile,String username,String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityUtils.setSecurityManager(factory.getInstance());

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

    }

}
