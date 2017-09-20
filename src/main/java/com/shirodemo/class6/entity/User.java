package com.shirodemo.class6.entity;

import java.io.Serializable;

/**
 * User
 */
public class User implements Serializable{

    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean locked = Boolean.FALSE;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String salt, Boolean locked) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.locked = locked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public String getCredentialsSalt(){
        return username+salt;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
