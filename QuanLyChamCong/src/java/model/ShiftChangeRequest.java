/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.sql.Date;

public class ShiftChangeRequest {

    private int requestId;
    private Users userId;
    private Shift fromShiftId;
    private Shift toShiftId;
    private Date date;
    private String reason;
    private String status; // pending, approved, rejected
    private Timestamp createdAt;
    private Users approvedBy;
    private Timestamp approvedAt;

    public ShiftChangeRequest() {
    }

    public ShiftChangeRequest(int requestId, Users userId, Shift fromShiftId, Shift toShiftId, Date date, String reason, String status, Timestamp createdAt, Users approvedBy, Timestamp approvedAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.fromShiftId = fromShiftId;
        this.toShiftId = toShiftId;
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Shift getFromShiftId() {
        return fromShiftId;
    }

    public void setFromShiftId(Shift fromShiftId) {
        this.fromShiftId = fromShiftId;
    }

    public Shift getToShiftId() {
        return toShiftId;
    }

    public void setToShiftId(Shift toShiftId) {
        this.toShiftId = toShiftId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Users getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Users approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approvedAt) {
        this.approvedAt = approvedAt;
    }

    @Override
    public String toString() {
        return "ShiftChangeRequest{" + "requestId=" + requestId + ", userId=" + userId + ", fromShiftId=" + fromShiftId + ", toShiftId=" + toShiftId + ", date=" + date + ", reason=" + reason + ", status=" + status + ", createdAt=" + createdAt + ", approvedBy=" + approvedBy + ", approvedAt=" + approvedAt + '}';
    }

}
