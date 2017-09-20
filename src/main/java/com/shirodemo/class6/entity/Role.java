package com.shirodemo.class6.entity;

/**
 * Role
 */
public class Role {

    private Long id;
    private String role;//角色标识 程序中判断使用，如:"admin"
    private String description;//角色描述，UI界面使用
    private Boolean available = Boolean.FALSE;//是否可用，如果不可将不能添加给用户

    public Role() {
    }

    public Role(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
