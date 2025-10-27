package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String reportType;
    private String railwaySection;
    private String workType;
    private String description;
    private LocalDateTime createdDate;
    private String author;
    private String status; // DRAFT, SUBMITTED, APPROVED

    // геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getRailwaySection() { return railwaySection; }
    public void setRailwaySection(String railwaySection) { this.railwaySection = railwaySection; }

    public String getWorkType() { return workType; }
    public void setWorkType(String workType) { this.workType = workType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}