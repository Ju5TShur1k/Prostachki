package com.example.demo.repository;

import com.example.demo.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatusOrderByCreatedDateDesc(String status);
    List<Report> findAllByOrderByCreatedDateDesc();
    List<Report> findByAuthorOrderByCreatedDateDesc(String author);
}