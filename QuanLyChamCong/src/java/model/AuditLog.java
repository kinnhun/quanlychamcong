/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import io.opencensus.common.Timestamp;

public class AuditLog {

    private int logId;
    private Users user;
    private String action;
    private Timestamp createdAt;

    public AuditLog() {
    }

    public AuditLog(int logId, Users user, String action, Timestamp createdAt) {
        this.logId = logId;
        this.user = user;
        this.action = action;
        this.createdAt = createdAt;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AuditLog{" + "logId=" + logId + ", user=" + user + ", action=" + action + ", createdAt=" + createdAt + '}';
    }

}
