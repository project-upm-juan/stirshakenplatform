package com.upm.pfg.stirshakenplatform.database;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Country {

    @Id
    private String countryId;

    private String descripcion;

    private String stiInfo;
}