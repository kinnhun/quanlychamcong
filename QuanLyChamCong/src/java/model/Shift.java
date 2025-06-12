package model;

import java.sql.Time;
import java.util.Date;

public class Shift {

    private int shiftId;
    private String shiftName;
    private Time startTime;
    private Time endTime;
    private String description;
    private Date createdAt;

    public Shift() {
    }

    public Shift(int shiftId, String shiftName, Time startTime, Time endTime, String description, Date createdAt) {
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Shift{" + "shiftId=" + shiftId + ", shiftName=" + shiftName + ", startTime=" + startTime + ", endTime=" + endTime + ", description=" + description + ", createdAt=" + createdAt + '}';
    }

}
