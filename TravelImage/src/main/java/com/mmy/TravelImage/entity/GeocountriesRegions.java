package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "geocountries_regions")
public class GeocountriesRegions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ISO", nullable = false)
    private String ISO;

    @Column(name = "fipsCountry_RegionCode")
    private String fipscountryRegioncode;

    @Column(name = "ISO3")
    private String ISO3;

    @Column(name = "ISONumeric")
    private String ISONumeric;

    @Column(name = "Country_RegionName")
    private String countryRegionname;

    @Column(name = "Capital")
    private String capital;

    @Column(name = "GeoNameID")
    private Integer geoNameID;

    @Column(name = "Area")
    private String area;

    @Column(name = "Population")
    private Integer population;

    @Column(name = "Continent")
    private String continent;

    @Column(name = "TopLevelDomain")
    private String topLevelDomain;

    @Column(name = "CurrencyCode")
    private String currencyCode;

    @Column(name = "CurrencyName")
    private String currencyName;

    @Column(name = "PhoneCountry_RegionCode")
    private String phonecountryRegioncode;

    @Column(name = "Languages")
    private String languages;

    @Column(name = "PostalCodeFormat")
    private String postalCodeFormat;

    @Column(name = "PostalCodeRegex")
    private String postalCodeRegex;

    @Column(name = "Neighbours")
    private String neighbours;

    @Column(name = "Country_RegionDescription")
    private String countryRegiondescription;

}
