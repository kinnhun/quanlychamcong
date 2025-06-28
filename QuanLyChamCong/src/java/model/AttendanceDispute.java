/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

public class AttendanceDispute {

    private int disputeId;
    private Attendance attendanceId;
    private int userId;
    private String reason;
    private String status;
    private String managerComment;
    private Timestamp createdAt;
    private Timestamp resolvedAt;
    private String issueType;
    private String attachmentPath;
    private String history;
    private Timestamp updatedAt;
    private Integer lastUpdatedBy;

    public AttendanceDispute() {
    }

    public AttendanceDispute(int disputeId, Attendance attendanceId, int userId, String reason, String status, String managerComment, Timestamp createdAt, Timestamp resolvedAt, String issueType, String attachmentPath, String history, Timestamp updatedAt, Integer lastUpdatedBy) {
        this.disputeId = disputeId;
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.reason = reason;
        this.status = status;
        this.managerComment = managerComment;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.issueType = issueType;
        this.attachmentPath = attachmentPath;
        this.history = history;
        this.updatedAt = updatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(int disputeId) {
        this.disputeId = disputeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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

    public Attendance getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Attendance attendanceId) {
        this.attendanceId = attendanceId;
    }

    @Override
    public String toString() {
        return "AttendanceDispute{" + "disputeId=" + disputeId + ", attendanceId=" + attendanceId + ", userId=" + userId + ", reason=" + reason + ", status=" + status + ", managerComment=" + managerComment + ", createdAt=" + createdAt + ", resolvedAt=" + resolvedAt + ", issueType=" + issueType + ", attachmentPath=" + attachmentPath + ", history=" + history + ", updatedAt=" + updatedAt + ", lastUpdatedBy=" + lastUpdatedBy + '}';
    }

}
