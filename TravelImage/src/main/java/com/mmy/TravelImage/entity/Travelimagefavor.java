package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "travelimagefavor")
public class Travelimagefavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FavorID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favorID;

    @Column(name = "UID")
    private Integer UID;

    @Column(name = "ImageID")
    private Integer imageID;

}
