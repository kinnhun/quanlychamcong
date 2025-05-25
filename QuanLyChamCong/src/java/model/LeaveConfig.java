/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;


public class LeaveConfig {
 private int configId;
    private int year;
    private String leaveType;
    private int defaultDays;
    private Users createdBy;

    public LeaveConfig() {
    }

    public LeaveConfig(int configId, int year, String leaveType, int defaultDays, Users createdBy) {
        this.configId = configId;
        this.year = year;
        this.leaveType = leaveType;
        this.defaultDays = defaultDays;
        this.createdBy = createdBy;
    }
    

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getDefaultDays() {
        return defaultDays;
    }

    public void setDefaultDays(int defaultDays) {
        this.defaultDays = defaultDays;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "LeaveConfig{" + "configId=" + configId + ", year=" + year + ", leaveType=" + leaveType + ", defaultDays=" + defaultDays + ", createdBy=" + createdBy + '}';
    }
    
}
