package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.Travelimage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TravelimageRepository extends JpaRepository<Travelimage, Integer>, JpaSpecificationExecutor<Travelimage> {

}
