package com.shirodemo.class6.entity;

import java.io.Serializable;

/**
 * Permission
 */
public class Permission implements Serializable{

    private Long id;
    private String permission;//权限标识 程序中使用，如："user:create"
    private String description;//权限描述 UI界面显示使用
    private Boolean avaliable = Boolean.FALSE;

    public Permission() {
    }

    public Permission(String permission, String description, Boolean avaliable) {
        this.permission = permission;
        this.description = description;
        this.avaliable = avaliable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }
}
