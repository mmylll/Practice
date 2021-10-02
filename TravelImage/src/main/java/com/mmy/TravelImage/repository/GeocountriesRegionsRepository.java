package com.mmy.TravelImage.repository;

import com.mmy.TravelImage.entity.GeocountriesRegions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GeocountriesRegionsRepository extends JpaRepository<GeocountriesRegions, String>, JpaSpecificationExecutor<GeocountriesRegions> {

}
