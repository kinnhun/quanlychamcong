/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class LocationDepartments {

    private Locations locationId;
    private Departments departmentId;

    public LocationDepartments() {
    }

    public LocationDepartments(Locations locationId, Departments departmentId) {
        this.locationId = locationId;
        this.departmentId = departmentId;
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

}
