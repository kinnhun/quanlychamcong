/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class LeaveBalance {

    private int leaveBalanceId;
    private Users user;
    private int year;
    private String leaveType;
    private int totalDays;
    private int usedDays;

    public LeaveBalance() {
    }

    public LeaveBalance(int leaveBalanceId, Users user, int year, String leaveType, int totalDays, int usedDays) {
        this.leaveBalanceId = leaveBalanceId;
        this.user = user;
        this.year = year;
        this.leaveType = leaveType;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
    }

    public int getLeaveBalanceId() {
        return leaveBalanceId;
    }

    public void setLeaveBalanceId(int leaveBalanceId) {
        this.leaveBalanceId = leaveBalanceId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }

    @Override
    public String toString() {
        return "LeaveBalance{" + "leaveBalanceId=" + leaveBalanceId + ", user=" + user + ", year=" + year + ", leaveType=" + leaveType + ", totalDays=" + totalDays + ", usedDays=" + usedDays + '}';
    }

}
