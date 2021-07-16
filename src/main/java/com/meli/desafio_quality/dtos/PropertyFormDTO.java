package com.meli.desafio_quality.dtos;

import com.meli.desafio_quality.models.Property;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyFormDTO {
    @NotNull(message = "O nome da propriedade nao pode estar vazio.")
    @Size(max = 30, message = "O comprimento do nome nao pode exceder 30 caracteres")
    @Pattern(regexp = "^[A-Z][A-Za-z0-9_ ]*$", message = "O nome da propriedade deve comecar com uma letra maiuscula.")
    private String prop_name;
    @NotNull
    @Size(max = 45, message = "O comprimento do bairro nao pode exceder 45 caracteres")
    private String prop_district;
    @NotNull
    @NotEmpty
    @Valid
    private List<RoomDTO> rooms;

    public PropertyFormDTO(String name, String district, List<RoomDTO> rooms) {
        this.prop_name = name;
        this.prop_district = district;
        this.rooms = rooms;
    }

    public String getProp_name() {
        return prop_name;
    }

    public void setProp_name(String prop_name) {
        this.prop_name = prop_name;
    }

    public String getProp_district() {
        return prop_district;
    }

    public void setProp_district(String prop_district) {
        this.prop_district = prop_district;
    }


    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public Property convert() {
        return new Property(
                this.prop_name,
                this.prop_district,
                this.rooms.stream()
                .map(RoomDTO::convert)
                .collect(Collectors.toList()));
    }
}
