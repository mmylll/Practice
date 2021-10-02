package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.Geocontinents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GeocontinentsRepository extends JpaRepository<Geocontinents, String>, JpaSpecificationExecutor<Geocontinents> {

}
