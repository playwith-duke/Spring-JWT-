package com.example.demo;

import com.example.demo.bean.UserRole;
import java.util.List;

public class DisplayUser {

    private int id;
    private String username;
    private List<UserRole> roles;

    public DisplayUser(int id, String username, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
