package com.meli.desafio_quality.dtos;

public class RoomWithAreaDTO extends RoomDTO{

    private double area;

    public RoomWithAreaDTO(String name, double width, double length, double area) {
        super(name, width, length);
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
