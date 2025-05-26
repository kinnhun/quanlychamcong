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
    private int id;
    private String name;
    private String address;
    private String ipMap;
    private boolean isActive;

    public Locations() {
    }

    public Locations(int id, String name, String address, String ipMap, boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.ipMap = ipMap;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIpMap() {
        return ipMap;
    }

    public void setIpMap(String ipMap) {
        this.ipMap = ipMap;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Locations{" + "id=" + id + ", name=" + name + ", address=" + address + ", ipMap=" + ipMap + ", isActive=" + isActive + '}';
    }
    
    

    
}
