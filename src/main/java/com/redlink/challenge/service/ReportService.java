package com.redlink.challenge.service;

import com.redlink.challenge.model.LoanView;
import com.redlink.challenge.repository.LoanViewRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final LoanViewRepository loanViewRepository;

    public ReportService(LoanViewRepository loanViewRepository) {
        this.loanViewRepository = loanViewRepository;
    }

    public byte[] buildDailyReportCsv(LocalDate date) {
        List<LoanView> views = loanViewRepository.findByDate(date);

        StringBuilder sb = new StringBuilder();
        sb.append("dni,viewedAt").append("\n");
        for (LoanView v : views) {
            sb.append(v.getDni()).append(",").append(v.getViewedAt()).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
