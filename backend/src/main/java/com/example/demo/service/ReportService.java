package com.example.demo.service;

import com.example.demo.model.Report;
import com.example.demo.model.dto.CreateReportRequest;
import com.example.demo.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(CreateReportRequest request, String author) {
        Report report = new Report();
        report.setTitle(request.getTitle());
        report.setReportType(request.getReportType());
        report.setRailwaySection(request.getRailwaySection());
        report.setWorkType(request.getWorkType());
        report.setDescription(request.getDescription());
        report.setAuthor(author);
        report.setCreatedDate(LocalDateTime.now());
        report.setStatus("SUBMITTED");

        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAllByOrderByCreatedDateDesc();
    }

    public List<Report> getUserReports(String author) {
        return reportRepository.findByAuthorOrderByCreatedDateDesc(author);
    }

    public List<Report> getApprovedReports() {
        return reportRepository.findByStatusOrderByCreatedDateDesc("SUBMITTED");
    }
}