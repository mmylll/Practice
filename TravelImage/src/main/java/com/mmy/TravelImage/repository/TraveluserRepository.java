package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.Traveluser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TraveluserRepository extends JpaRepository<Traveluser, Integer>, JpaSpecificationExecutor<Traveluser> {

}
