package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "travelimage")
public class Travelimage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ImageID", nullable = false)
    private Integer imageID;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    @Column(name = "CityCode")
    private Integer cityCode;

    @Column(name = "Country_RegionCodeISO")
    private String countryRegioncodeiso;

    @Column(name = "UID")
    private Integer UID;

    @Column(name = "PATH")
    private String PATH;

    @Column(name = "Content")
    private String content;

}
