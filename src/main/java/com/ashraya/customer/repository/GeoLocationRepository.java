package com.ashraya.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.GeoLocation;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Integer> {

    @Query(value = "SELECT *, SQRT(POW(69.1 * (latitude - :lat), 2) + POW(69.1 * (:longitude - longitude) * COS(latitude / 57.3), 2)) AS distance FROM geo_location HAVING distance < :radius ORDER BY distance", nativeQuery = true)
    public List<GeoLocation> findByGeoLocation(@Param("lat") Double lat, @Param("longitude") Double longitude, @Param("radius") float radius);
}
