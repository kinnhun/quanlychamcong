/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kinkin
 */
public class UserLocations {

    private int userLocationId;
    private Users user;
    private Locations location;
    private boolean isDefault;

    public UserLocations() {
    }

    public UserLocations(int userLocationId, Users user, Locations location, boolean isDefault) {
        this.userLocationId = userLocationId;
        this.user = user;
        this.location = location;
        this.isDefault = isDefault;
    }

    public int getUserLocationId() {
        return userLocationId;
    }

    public void setUserLocationId(int userLocationId) {
        this.userLocationId = userLocationId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "UserLocations{" + "userLocationId=" + userLocationId + ", user=" + user + ", location=" + location + ", isDefault=" + isDefault + '}';
    }

}
