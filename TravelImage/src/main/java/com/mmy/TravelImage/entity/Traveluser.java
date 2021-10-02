package com.mmy.TravelImage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "traveluser")
public class Traveluser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;

    @Column(name = "Email")
    private String email;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Pass")
    private String pass;

    @Column(name = "State")
    private Integer state;

    @Column(name = "DateJoined")
    private Date dateJoined;

    @Column(name = "DateLastModified")
    private Date dateLastModified;

}
