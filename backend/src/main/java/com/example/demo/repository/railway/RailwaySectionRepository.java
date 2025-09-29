package com.example.demo.repository.railway;

import com.example.demo.model.railway.RailwaySection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RailwaySectionRepository extends JpaRepository<RailwaySection, Long> {
    Optional<RailwaySection> findByKilometer(String kilometer);
    List<RailwaySection> findAllByOrderByKilometerAsc();
    List<RailwaySection> findByRegion(String region);
}