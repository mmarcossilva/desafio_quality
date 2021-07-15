package com.meli.desafio_quality.repository;

import com.meli.desafio_quality.models.District;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DistrictRepository {
    private List<District> districts;

    public DistrictRepository() {
        this.districts = new ArrayList<>();
    }

    public District getDistrictByName(String name){
        return districts.stream()
                .filter(district -> district.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void addDistrict(District district){
        this.districts.add(district);
    }
}
