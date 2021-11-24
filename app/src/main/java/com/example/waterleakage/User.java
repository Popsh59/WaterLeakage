package com.example.waterleakage;

public class User
{
    private String name;
    private String email;
    private String cell;
    private String password;
    private String role;

    public User(){

    }

    public User(String name, String email, String cell, String password, String role) {
        this.name = name;
        this.email = email;
        this.cell = cell;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
