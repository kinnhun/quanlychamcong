/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.Date;


public class Attendance {
       private int attendanceId;
    private Users user;
    private Date date;
    private Timestamp checkinTime;
    private Timestamp checkoutTime;
    private Locations location;
    private String checkinImageUrl;
    private String checkoutImageUrl;
    private boolean isLocked;
    private Timestamp createdAt;

    public Attendance() {
    }

    public Attendance(int attendanceId, Users user, Date date, Timestamp checkinTime, Timestamp checkoutTime, Locations location, String checkinImageUrl, String checkoutImageUrl, boolean isLocked, Timestamp createdAt) {
        this.attendanceId = attendanceId;
        this.user = user;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.location = location;
        this.checkinImageUrl = checkinImageUrl;
        this.checkoutImageUrl = checkoutImageUrl;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Timestamp checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Timestamp getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Timestamp checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public String getCheckinImageUrl() {
        return checkinImageUrl;
    }

    public void setCheckinImageUrl(String checkinImageUrl) {
        this.checkinImageUrl = checkinImageUrl;
    }

    public String getCheckoutImageUrl() {
        return checkoutImageUrl;
    }

    public void setCheckoutImageUrl(String checkoutImageUrl) {
        this.checkoutImageUrl = checkoutImageUrl;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Attendance{" + "attendanceId=" + attendanceId + ", user=" + user + ", date=" + date + ", checkinTime=" + checkinTime + ", checkoutTime=" + checkoutTime + ", location=" + location + ", checkinImageUrl=" + checkinImageUrl + ", checkoutImageUrl=" + checkoutImageUrl + ", isLocked=" + isLocked + ", createdAt=" + createdAt + '}';
    }
    
    
    
}
