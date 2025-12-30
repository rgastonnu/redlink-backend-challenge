package com.redlink.challenge.service;

import com.redlink.challenge.model.EmployeeCredit;
import com.redlink.challenge.repository.EmployeeCreditRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class ImportService {

    private final EmployeeCreditRepository employeeCreditRepository;

    public ImportService(EmployeeCreditRepository employeeCreditRepository) {
        this.employeeCreditRepository = employeeCreditRepository;
    }

    /**
     * CSV esperado:
     * dni,amount
     * 30123456,150000
     * 30987654,0
     */
    public void importCsv(InputStream csvStream) {
        employeeCreditRepository.clear();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (first) { // header
                    first = false;
                    if (line.toLowerCase().startsWith("dni")) continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) continue; // MVP: ignoramos líneas inválidas

                String dni = parts[0].trim();
                BigDecimal amount = new BigDecimal(parts[1].trim());

                employeeCreditRepository.save(new EmployeeCredit(dni, amount));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to import CSV", e);
        }
    }
}
