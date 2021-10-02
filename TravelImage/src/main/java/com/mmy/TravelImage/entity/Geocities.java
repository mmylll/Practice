package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "geocities")
public class Geocities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "GeoNameID", nullable = false)
    private Integer geoNameID;

    @Column(name = "AsciiName")
    private String asciiName;

    @Column(name = "Country_RegionCodeISO")
    private String countryRegioncodeiso;

    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    @Column(name = "FeatureCode")
    private String featureCode;

    @Column(name = "Admin1Code")
    private Integer admin1Code;

    @Column(name = "Admin2Code")
    private String admin2Code;

    @Column(name = "Population")
    private Integer population;

    @Column(name = "Elevation")
    private Integer elevation;

    @Column(name = "TimeZone")
    private String timeZone;

}
