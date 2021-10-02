package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.Travelimagefavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TravelimagefavorRepository extends JpaRepository<Travelimagefavor, Integer>, JpaSpecificationExecutor<Travelimagefavor> {

}
