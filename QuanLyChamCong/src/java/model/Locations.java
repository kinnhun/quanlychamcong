/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kinkin
 */
public class Locations {
     private int locationId;
    private String name;
    private String address;
    private boolean isActive;

    public Locations() {
    }

    public Locations(int locationId, String name, String address, boolean isActive) {
        this.locationId = locationId;
        this.name = name;
        this.address = address;
        this.isActive = isActive;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Locations{" + "locationId=" + locationId + ", name=" + name + ", address=" + address + ", isActive=" + isActive + '}';
    }

    
}
