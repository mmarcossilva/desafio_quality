package com.meli.desafio_quality.service;

import com.meli.desafio_quality.exceptions.DistrictNotFoundException;
import com.meli.desafio_quality.exceptions.PropertyWhithoutRoomsException;
import com.meli.desafio_quality.models.District;
import com.meli.desafio_quality.models.Property;
import com.meli.desafio_quality.models.Room;
import com.meli.desafio_quality.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
public class PropertyService {

    private DistrictRepository districtRepository;

    @Autowired
    public PropertyService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public PropertyService() {

    }

    public double calculateAreaOfProperty(Property property){
        return property.getRooms().stream().mapToDouble(this::getRoomArea).sum();
    }

    public BigDecimal calculateValueOfProperty(Property property){
        District district = this.districtRepository.getDistrictByName(property.getDistrict());
        if(district == null)
            throw new DistrictNotFoundException();
        return district.getValueM2().multiply(BigDecimal.valueOf(this.calculateAreaOfProperty(property)));
    }


    public Room findLargestRoom(Property property){
        return property.getRooms()
                .stream()
                .max(Comparator.comparingDouble(this::getRoomArea))
                .orElseThrow(PropertyWhithoutRoomsException::new);
    }

    public double getRoomArea(Room room){
        return room.getLength() * room.getWidth();
    }


}
