package com.kubgtu.car_school.controller_tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubgtu.car_school.config.SecurityConfig;
import com.kubgtu.car_school.model.DTO.UserDTO;
import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import com.kubgtu.car_school.model.RequestStatusTypes;
import com.kubgtu.car_school.service.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestService requestService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public RequestService requestService() {
            return Mockito.mock(RequestService.class);
        }
    }

    private final UUID userId = UUID.randomUUID();

    private final UserDTO userDTO = new UserDTO(userId, "lastname", "secondment");

    private final UserRequestDTO userRequestDTO = new UserRequestDTO(
            1L,
            userDTO,
            "123-456-7890",
            LocalDateTime.of(2025, 5, 13, 10, 0),
            RequestStatusTypes.NEW
    );

    @Test
    @WithMockUser(roles = "ADMIN")
    void getRequestById_shouldReturnUserRequestDTO() throws Exception {
        Mockito.when(requestService.getById(1L)).thenReturn(userRequestDTO);

        mockMvc.perform(get("/api/request/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.first_name").value("lastname"))
                .andExpect(jsonPath("$.user.last_name").value("secondment"))
                .andExpect(jsonPath("$.telephone").value("123-456-7890"))
                .andExpect(jsonPath("$.time_to_call").value("2025-05-13T10:00:00"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }
}
