/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import io.opencensus.common.Timestamp;

/**
 *
 * @author kinkin
 */
public class UserLocations {

    private int id;
    private Users userId;
    private Locations locationId;
    private Timestamp assignedAt;
    private Departments departmentId;
    
    

    public UserLocations() {
    }

    public UserLocations(int id, Users userId, Locations locationId, Timestamp assignedAt, Departments departmentId) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.assignedAt = assignedAt;
        this.departmentId = departmentId;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Locations getLocationId() {
        return locationId;
    }

    public void setLocationId(Locations locationId) {
        this.locationId = locationId;
    }

    public Departments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Departments departmentId) {
        this.departmentId = departmentId;
    }

   

    public Timestamp getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Timestamp assignedAt) {
        this.assignedAt = assignedAt;
    }

    @Override
    public String toString() {
        return "UserLocations{" + "id=" + id + ", userId=" + userId + ", locationId=" + locationId + ", assignedAt=" + assignedAt + '}';
    }

}
