package com.example.demo.model.dto;

public class CreateReportRequest {
    private String title;
    private String reportType;
    private String railwaySection;
    private String workType;
    private String description;

    // геттеры и сеттеры
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
}