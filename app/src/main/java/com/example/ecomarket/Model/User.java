package com.example.ecomarket.Model;

public class User {
    private String username;
    private String id;
    private String email;

    private String pdp;
    private String pwd;
    private String role;

    public User(String username, String id, String email, String pdp, String pwd, String role) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.pdp = pdp;
        this.pwd = pwd;
        this.role = role;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPdp() {
        return pdp;
    }

    public void setPdp(String pdp) {
        this.pdp = pdp;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
