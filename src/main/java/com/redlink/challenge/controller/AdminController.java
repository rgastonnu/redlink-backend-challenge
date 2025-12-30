package com.redlink.challenge.controller;

import com.redlink.challenge.service.ImportService;
import com.redlink.challenge.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ImportService importService;
    private final ReportService reportService;

    public AdminController(ImportService importService, ReportService reportService) {
        this.importService = importService;
        this.reportService = reportService;
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importDailyFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            importService.importCsv(file.getInputStream());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> dailyReport(@RequestParam(value = "date", required = false) String date) {
        LocalDate targetDate = LocalDate.now();
        if (date != null && !date.isBlank()) {
            try {
                targetDate = LocalDate.parse(date.trim());
            } catch (DateTimeParseException ex) {
                return ResponseEntity.badRequest().build();
            }
        }

        byte[] csv = reportService.buildDailyReportCsv(targetDate);

        String filename = "reporte_" + targetDate + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(new MediaType("text", "csv"))
                .body(csv);
    }
}
