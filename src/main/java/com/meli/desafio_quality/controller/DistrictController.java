package com.meli.desafio_quality.controller;

import com.meli.desafio_quality.dtos.DistrictDTO;
import com.meli.desafio_quality.service.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/district")
public class DistrictController {

    private DistrictService service;

    public DistrictController(DistrictService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> createDistrict(@Valid @RequestBody DistrictDTO districtDTO){
        service.createDistrict(districtDTO.convert());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
