package com.meli.desafio_quality.controller;

import com.meli.desafio_quality.dtos.PropertyFormDTO;
import com.meli.desafio_quality.dtos.RoomWithAreaDTO;
import com.meli.desafio_quality.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService service;

    @Autowired
    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @PostMapping("/totalArea")
    public ResponseEntity<String> getArea(@Valid @RequestBody PropertyFormDTO propertyFormDTO){
        return new ResponseEntity<>(
                "A area total é: " + service.calculateAreaOfProperty(propertyFormDTO.convert()),
                HttpStatus.OK);
    }

    @PostMapping("/value")
    public ResponseEntity<String> getPropertyValue(@Valid @RequestBody PropertyFormDTO propertyFormDTO){
        return new ResponseEntity<>(
                "O valor da propriedade é: " + service.calculateValueOfProperty(propertyFormDTO.convert()),
                HttpStatus.OK);
    }

    @PostMapping("/largestRoom")
    public ResponseEntity<String> findLargestRoom(@Valid @RequestBody PropertyFormDTO propertyFormDTO){
        return new ResponseEntity<>(
                "O maior cômodo é o: " + service.findLargestRoom(propertyFormDTO.convert()).getName(),
                HttpStatus.OK);
    }

    @PostMapping("/rooms/area")
    public ResponseEntity<List<RoomWithAreaDTO>> getRoom(@Valid @RequestBody PropertyFormDTO propertyFormDTO){
        return new ResponseEntity<>(service.getRoomsWithArea(propertyFormDTO.convert()), HttpStatus.OK);
    }

}
