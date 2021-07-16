package com.meli.desafio_quality.dtos;

import com.meli.desafio_quality.models.District;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class DistrictDTO {
    @NotNull
    @Size(max = 45, message = "O comprimento do bairro nao pode exceder 45 caracteres")
    private String name;
    @NotNull(message = "O valor do metro quadrado do bairro nao pode estar vazio")
    @Digits(integer = 13, fraction = 0)
    private BigDecimal district_m2;

    public DistrictDTO(String name, BigDecimal district_m2) {
        this.name = name;
        this.district_m2 = district_m2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDistrict_m2() {
        return district_m2;
    }

    public void setDistrict_m2(BigDecimal district_m2) {
        this.district_m2 = district_m2;
    }

    public District convert() {
        return new District(this.name, this.district_m2);
    }
}
