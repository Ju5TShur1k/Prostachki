package com.example.demo.model.railway;

import jakarta.persistence.*;

@Entity
@Table(name = "railway_sections")
public class RailwaySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kilometer", unique = true)
    private String kilometer; // Например: "km125", "km130-135"

    private String sectionName;
    private String description;
    private String region;
    private String status; // "active", "maintenance", etc.

    // Конструкторы, геттеры, сеттеры
    public RailwaySection() {}

    public RailwaySection(String kilometer, String sectionName, String description) {
        this.kilometer = kilometer;
        this.sectionName = sectionName;
        this.description = description;
    }

    // Геттеры и сеттеры...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKilometer() { return kilometer; }
    public void setKilometer(String kilometer) { this.kilometer = kilometer; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}