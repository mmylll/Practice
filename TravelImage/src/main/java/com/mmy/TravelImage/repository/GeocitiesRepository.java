package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.Geocities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GeocitiesRepository extends JpaRepository<Geocities, Integer>, JpaSpecificationExecutor<Geocities> {

}
