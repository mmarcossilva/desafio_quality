package com.meli.desafio_quality.service;

import com.meli.desafio_quality.exceptions.DistrictNotFoundException;
import com.meli.desafio_quality.models.District;
import com.meli.desafio_quality.models.Property;
import com.meli.desafio_quality.models.Room;
import com.meli.desafio_quality.repository.DistrictRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PropertyServiceTest {

    @MockBean
    private DistrictRepository districtRepositoryMock;

    @InjectMocks
    private PropertyService propertyService = new PropertyService();

    Room room1;
    Room room2;
    Room room3;
    Property property;
    District district;

    @BeforeEach
    void setUp() {
        String districtName = "Estreito";
        room1 = new Room("SalaGrande", 10, 10);
        room2 = new Room("SalaPequena", 5, 5);
        room3 = new Room("Banheiro", 2, 2);
        property = new Property("Casa", districtName, new ArrayList<>(Arrays.asList(room3,room1,room2)));
        district = new District(districtName, new BigDecimal(1000));
    }

    @Test
    void testCalculateAreaOfProperty() {
        assertEquals(129, this.propertyService.calculateAreaOfProperty(property));
    }

    @Test
    void testCalculateValueOfProperty() {
        Mockito.when(districtRepositoryMock.getDistrictByName(property.getDistrict())).thenReturn(district);

        assertEquals(new BigDecimal("129000.0"), this.propertyService.calculateValueOfProperty(property));
    }

    @Test
    void testCalculateValueOfPropertyDistrictNotFound() {
        Mockito.when(districtRepositoryMock.getDistrictByName(property.getDistrict())).thenReturn(null);

        assertThrows(DistrictNotFoundException.class, () -> this.propertyService.calculateValueOfProperty(property));
    }

    @Test
    void testGetBiggestRoom() {
        assertSame(room1, this.propertyService.findLargestRoom(property));
    }

    @Test
    void testGetRoomArea() {
        assertEquals(100, this.propertyService.getRoomArea(room1));
    }
}