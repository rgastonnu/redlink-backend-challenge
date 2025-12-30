package com.redlink.challenge.repository;

import com.redlink.challenge.model.EmployeeCredit;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmployeeCreditRepository {

    private final Map<String, EmployeeCredit> store = new ConcurrentHashMap<>();

    public Optional<EmployeeCredit> findByDni(String dni) {
        return Optional.ofNullable(store.get(dni));
    }

    public void save(EmployeeCredit credit) {
        store.put(credit.getDni(), credit);
    }

    public void clear() {
        store.clear();
    }
}
