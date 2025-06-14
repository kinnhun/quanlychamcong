package model;

import java.util.Date;

public class UserShift {

    private int id;
    private Users user;
    private Shift shift;
    private Date date;
    private Locations location;
    private Departments department;
    private Users assignedBy;
    private Date assignedAt;
    private String note;

    public UserShift() {
    }

    public UserShift(int id, Users user, Shift shift, Date date, Locations location, Departments department, Users assignedBy, Date assignedAt, String note) {
        this.id = id;
        this.user = user;
        this.shift = shift;
        this.date = date;
        this.location = location;
        this.department = department;
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    public Users getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Users assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "UserShift{" + "id=" + id + ", user=" + user + ", shift=" + shift + ", date=" + date + ", location=" + location + ", department=" + department + ", assignedBy=" + assignedBy + ", assignedAt=" + assignedAt + ", note=" + note + '}';
    }

}
