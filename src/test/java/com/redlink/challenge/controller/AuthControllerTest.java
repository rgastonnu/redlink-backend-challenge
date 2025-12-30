package com.redlink.challenge.controller;

import com.redlink.challenge.service.AuthService;
import com.redlink.challenge.service.EmployeeNotEligibleException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void login_ok() throws Exception {
        doNothing().when(authService).assertEligible("12345678");

        mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dni\":\"12345678\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.eligible").value(true));
    }

    @Test
    void login_not_eligible_returns_404() throws Exception {
        doThrow(new EmployeeNotEligibleException("00000000"))
                .when(authService).assertEligible("00000000");

        mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dni\":\"00000000\"}")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.dni").value("00000000"))
                .andExpect(jsonPath("$.eligible").value(false));
    }

    @Test
    void login_blank_dni_returns_400() throws Exception {
        mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dni\":\"\"}")
        )
                .andExpect(status().isBadRequest());
    }
}
