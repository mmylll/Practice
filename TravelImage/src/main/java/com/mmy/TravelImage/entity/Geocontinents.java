package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "geocontinents")
public class Geocontinents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ContinentCode", nullable = false)
    private String continentCode;

    @Column(name = "ContinentName")
    private String continentName;

    @Column(name = "GeoNameId")
    private Integer geoNameId;

}
