package com.redlink.challenge.repository;

import com.redlink.challenge.model.LoanView;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LoanViewRepository {

    private final List<LoanView> views = new ArrayList<>();

    public void save(LoanView view) {
        views.add(view);
    }

    public List<LoanView> findByDate(LocalDate date) {
        return views.stream()
                .filter(v -> v.getViewedAt().toLocalDate().equals(date))
                .toList();
    }
}
