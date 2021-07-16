package com.meli.desafio_quality.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafio_quality.dtos.PropertyFormDTO;
import com.meli.desafio_quality.dtos.RoomDTO;
import com.meli.desafio_quality.dtos.RoomWithAreaDTO;
import com.meli.desafio_quality.exceptions.handlers.ValidationError;
import com.meli.desafio_quality.models.District;
import com.meli.desafio_quality.service.DistrictService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class PropertyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DistrictService districtService;

    RoomDTO room1;
    RoomDTO room2;
    RoomDTO room3;
    PropertyFormDTO property;
    District district;

    @BeforeEach
    void setUp() {
        String districtName = "Estreito";
        room1 = new RoomDTO("SalaGrande", 10, 10);
        room2 = new RoomDTO("SalaPequena", 5, 5);
        room3 = new RoomDTO("Banheiro", 2, 2);
        property = new PropertyFormDTO("Casa", districtName, new ArrayList<>(Arrays.asList(room3,room1,room2)));
        district = new District(districtName, new BigDecimal(1000));
        districtService.createDistrict(district);
    }

    @Test
    void testGetArea() throws Exception {
        String payLoad = mapper.writeValueAsString(property);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isOk())
        .andExpect(mvcResult -> assertEquals("A area total é: 129.0", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetAreaValidationErrorNameUpperCase() throws Exception {
        property.setProp_name("nome");
        String payLoad = mapper.writeValueAsString(property);
        List<ValidationError> validationError = new ArrayList<>(Collections.singletonList(new ValidationError("prop_name", "O nome da propriedade deve comecar com uma letra maiuscula.")));
        String response = mapper.writeValueAsString(validationError);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(response, mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetAreaValidationErrorNameNull() throws Exception {
        property.setProp_name(null);
        String payLoad = mapper.writeValueAsString(property);
        List<ValidationError> validationError = new ArrayList<>(Collections.singletonList(new ValidationError("prop_name", "O nome da propriedade nao pode estar vazio.")));
        String response = mapper.writeValueAsString(validationError);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(response, mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetAreaValidationErrorNameSize() throws Exception {
        property.setProp_name("Nome muito grande para ser usado da propriedade");
        String payLoad = mapper.writeValueAsString(property);
        List<ValidationError> validationError = new ArrayList<>(
                Collections.singletonList(new ValidationError("prop_name", "O comprimento do nome nao pode exceder 30 caracteres")));
        String response = mapper.writeValueAsString(validationError);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(response, mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetAreaValidationErrorDistrictNameSize() throws Exception {
        property.setProp_district("Nome muito grande para ser usado da no bairro 1234567890");
        String payLoad = mapper.writeValueAsString(property);
        List<ValidationError> validationError = new ArrayList<>(
                Collections.singletonList(new ValidationError("prop_district", "O comprimento do bairro nao pode exceder 45 caracteres")));
        String response = mapper.writeValueAsString(validationError);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(response, mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetAreaValidationErrorRoomLength() throws Exception {
        room3.setLength(2000);
        ValidationError roomLength = new ValidationError("rooms[0].length","O comprimento maximo permito por comodo e de 33 metros");

        List<ValidationError> validationErrors = new ArrayList<>(Collections.singletonList(roomLength));

        String payLoad = mapper.writeValueAsString(property);
        String response = mapper.writeValueAsString(validationErrors);
        mvc.perform(post("/property/totalArea")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(response, mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetPropertyValue() throws Exception {
        String payLoad = mapper.writeValueAsString(property);
        mvc.perform(post("/property/value")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> assertEquals("O valor da propriedade é: 129000.0", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testFindLargestRoom() throws Exception {
        String payLoad = mapper.writeValueAsString(property);
        mvc.perform(post("/property/largestRoom")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> assertEquals("O maior cômodo é o: SalaGrande", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void testGetRoom() throws Exception {
        property.setRooms(new ArrayList<>(Collections.singletonList(room1)));
        String payLoad = mapper.writeValueAsString(property);
        mvc.perform(post("/property/rooms/area")
                .contentType("application/json")
                .content(payLoad))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> assertEquals(100.0,
                        mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<RoomWithAreaDTO>>() {
                        }).get(0).getArea()));
    }
}