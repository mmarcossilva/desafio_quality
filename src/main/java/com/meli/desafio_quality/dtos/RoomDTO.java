package com.meli.desafio_quality.dtos;

import com.meli.desafio_quality.models.Room;

import javax.validation.constraints.*;

public class RoomDTO {
    @NotNull(message = "O nome do cômodo não pode estar vazio.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres")
    @Pattern(regexp = "\\b[A-Z]\\w*\\b", message = "O nome do cômodo deve começar com uma letra maiúscula.")
    private String name;

    @NotNull(message = "A largura do cômodo não pode estar vazio.")
    @Max(value = 25, message = "A largura máxima permita por cômodo é de 25 metros")
    @Min(0)
    private double width;

    @NotNull(message = "O comprimento do cômodo não pode estar vazio.")
    @Max(value = 33, message = "O comprimento máximo permito por cômodo é de 33 metros")
    @Min(0)
    private double length;

    public RoomDTO(String name, double width, double length) {
        this.name = name;
        this.width = width;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Room convert(){
        return new Room(this.name, this.width, this.length);
    }
}
