/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import io.opencensus.common.Timestamp;

public class AttendanceDispute {

    private int disputeId;
    private Attendance attendance;
    private Users user;
    private String reason;
    private String status;
    private String managerComment;
    private Timestamp createdAt;
    private Timestamp resolvedAt;

    public AttendanceDispute() {
    }

    public AttendanceDispute(int disputeId, Attendance attendance, Users user, String reason, String status, String managerComment, Timestamp createdAt, Timestamp resolvedAt) {
        this.disputeId = disputeId;
        this.attendance = attendance;
        this.user = user;
        this.reason = reason;
        this.status = status;
        this.managerComment = managerComment;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public int getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(int disputeId) {
        this.disputeId = disputeId;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Timestamp resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    @Override
    public String toString() {
        return "AttendanceDispute{" + "disputeId=" + disputeId + ", attendance=" + attendance + ", user=" + user + ", reason=" + reason + ", status=" + status + ", managerComment=" + managerComment + ", createdAt=" + createdAt + ", resolvedAt=" + resolvedAt + '}';
    }

}
