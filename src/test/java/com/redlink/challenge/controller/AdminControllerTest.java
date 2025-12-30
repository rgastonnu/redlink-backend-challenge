package com.redlink.challenge.controller;

import com.redlink.challenge.service.ImportService;
import com.redlink.challenge.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportService importService;

    // Si tu AdminController también inyecta ReportService, dejalo:
    @MockBean
    private ReportService reportService;

    @Test
    void import_ok() throws Exception {
        doNothing().when(importService).importCsv(any());

        MockMultipartFile file = new MockMultipartFile(
                "file", "daily.csv", "text/csv",
                "dni,amount\n12345678,100000\n".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/admin/import").file(file))
                .andExpect(status().isOk());
    }
}
