package com.meli.desafio_quality.service;

import com.meli.desafio_quality.models.District;
import com.meli.desafio_quality.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {

    public DistrictRepository repository;

    @Autowired
    public DistrictService(DistrictRepository repository) {
        this.repository = repository;
    }

    public void createDistrict(District district){
        repository.addDistrict(district);
    }
}
