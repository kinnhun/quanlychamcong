/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class LeaveType {

    private int leaveTypeId;
    private String leaveTypeName;
    private String status;

    public LeaveType() {
    }

    public LeaveType(int leaveTypeId, String leaveTypeName, String status) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

 

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    @Override
    public String toString() {
        return "LeaveType{" + "leaveTypeId=" + leaveTypeId + ", leaveTypeName=" + leaveTypeName + ", status=" + status + '}';
    }


}
