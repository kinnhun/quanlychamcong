/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class LeaveConfig {

    private int configId;
    private int year;
    private int defaultDays;
    private Users createdBy;
    private LeaveType leaveTypeId;


    public LeaveConfig() {
    }

    public LeaveConfig(int configId, int year, int defaultDays, Users createdBy, LeaveType leaveTypeId) {
        this.configId = configId;
        this.year = year;
        this.defaultDays = defaultDays;
        this.createdBy = createdBy;
        this.leaveTypeId = leaveTypeId;
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

    public LeaveType getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(LeaveType leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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
        return "LeaveConfig{" + "configId=" + configId + ", year=" + year + ", defaultDays=" + defaultDays + ", createdBy=" + createdBy + ", leaveTypeId=" + leaveTypeId + '}';
    }

 

}
