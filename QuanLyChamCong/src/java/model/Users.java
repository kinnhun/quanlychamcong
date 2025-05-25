/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author kinkin
 */
public class Users {
     private int userId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private String employmentType;
    private String status;
    private Timestamp createdAt;
    private String banReason;

    public Users() {
    }

    public Users(int userId, String username, String passwordHash, String fullName, String email, String phone, String role, String employmentType, String status, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.employmentType = employmentType;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Users(int userId, String username, String passwordHash, String fullName, String email, String phone, String role, String employmentType, String status, Timestamp createdAt, String banReason) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.employmentType = employmentType;
        this.status = status;
        this.createdAt = createdAt;
        this.banReason = banReason;
    }

   

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    @Override
    public String toString() {
        return "Users{" + "userId=" + userId + ", username=" + username + ", passwordHash=" + passwordHash + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", role=" + role + ", employmentType=" + employmentType + ", status=" + status + ", createdAt=" + createdAt + ", banReason=" + banReason + '}';
    }

 

    
    
}
