package com.meli.desafio_quality.models;

import java.util.List;

public class Property {
    private String name;
    private String district;
    private List<Room> rooms;

    public Property(String name, String district, List<Room> rooms) {
        this.name = name;
        this.district = district;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
