package com.example.demo.repository;

import com.example.demo.model.WindowConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WindowRepository extends JpaRepository<WindowConfiguration, Long> {
    List<WindowConfiguration> findByIsActiveTrue();
    List<WindowConfiguration> findByCreatedBy(String createdBy);
    List<WindowConfiguration> findByRailwaySectionAndIsActiveTrue(String railwaySection);
}