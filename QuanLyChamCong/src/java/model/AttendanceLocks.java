/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import io.opencensus.common.Timestamp;


public class AttendanceLocks {
 private int lockId;
    private int month;
    private int year;
    private Users lockedBy;
    private Timestamp lockedAt;

    public AttendanceLocks() {
    }

    public AttendanceLocks(int lockId, int month, int year, Users lockedBy, Timestamp lockedAt) {
        this.lockId = lockId;
        this.month = month;
        this.year = year;
        this.lockedBy = lockedBy;
        this.lockedAt = lockedAt;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Users getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(Users lockedBy) {
        this.lockedBy = lockedBy;
    }

    public Timestamp getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(Timestamp lockedAt) {
        this.lockedAt = lockedAt;
    }

    @Override
    public String toString() {
        return "AttendanceLocks{" + "lockId=" + lockId + ", month=" + month + ", year=" + year + ", lockedBy=" + lockedBy + ", lockedAt=" + lockedAt + '}';
    }
    
}
