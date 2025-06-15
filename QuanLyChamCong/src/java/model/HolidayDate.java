/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class HolidayDate {

    private int holidayId;
    private String holidayName;
    private Date holidayDate;
    private int year;
    private Date createdAt;

    public HolidayDate() {
    }

    public HolidayDate(int holidayId, String holidayName, Date holidayDate, int year, Date createdAt) {
        this.holidayId = holidayId;
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.year = year;
        this.createdAt = createdAt;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "HolidayDate{" + "holidayId=" + holidayId + ", holidayName=" + holidayName + ", holidayDate=" + holidayDate + ", year=" + year + ", createdAt=" + createdAt + '}';
    }

}
