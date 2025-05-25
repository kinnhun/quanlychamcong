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
    private int userId;
    private int locationId;
    private Timestamp assignedAt;

    public UserLocations() {
    }

    public UserLocations(int id, int userId, int locationId, Timestamp assignedAt) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.assignedAt = assignedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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
