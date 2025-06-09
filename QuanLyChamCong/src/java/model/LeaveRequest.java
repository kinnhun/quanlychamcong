/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class LeaveRequest {

    private int requestId;
    private Users user;
    private Date startDate;
    private Date endDate;
    private LeaveType leaveTypeId;
    private String status;
    private int daysCount;
    private String reason;
    private Timestamp createdAt;
    private Users approvedBy;
    private String approveComment;

    private List<Locations> locations;

    public LeaveRequest() {
    }

    public LeaveRequest(int requestId, Users user, Date startDate, Date endDate, LeaveType leaveTypeId, String status, int daysCount, String reason, Timestamp createdAt, Users approvedBy, String approveComment, List<Locations> locations) {
        this.requestId = requestId;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveTypeId = leaveTypeId;
        this.status = status;
        this.daysCount = daysCount;
        this.reason = reason;
        this.createdAt = createdAt;
        this.approvedBy = approvedBy;
        this.approveComment = approveComment;
        this.locations = locations;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LeaveType getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(LeaveType leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }

    @Override
    public String toString() {
        return "LeaveRequest{" + "requestId=" + requestId + ", user=" + user + ", startDate=" + startDate + ", endDate=" + endDate + ", leaveTypeId=" + leaveTypeId + ", status=" + status + ", daysCount=" + daysCount + ", reason=" + reason + ", createdAt=" + createdAt + ", approvedBy=" + approvedBy + ", approveComment=" + approveComment + ", locations=" + locations + '}';
    }

}
