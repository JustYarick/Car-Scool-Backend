package com.kubgtu.car_school.controller_tests;

import com.kubgtu.car_school.config.SecurityConfig;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.service.group.GroupService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class GroupsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupService groupService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public GroupService groupService() {
            return Mockito.mock(GroupService.class);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getGroupById_shouldReturnGroup() throws Exception {
        GroupDTO dto = new GroupDTO(1L, "Test Group", List.of());

        Mockito.when(groupService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group_id").value(1))
                .andExpect(jsonPath("$.group_name").value("Test Group"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getGroupById_shouldReturnGroup_WhenRoleIsAdmin() throws Exception {
        GroupDTO dto = new GroupDTO(1L, "Group A", List.of());

        Mockito.when(groupService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group_id").value(1))
                .andExpect(jsonPath("$.group_name").value("Group A"))
                .andExpect(jsonPath("$.student_uuid").isArray());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getGroupById_shouldReturnGroup_WhenRoleIsTeacher() throws Exception {
        GroupDTO dto = new GroupDTO(2L, "Group B", List.of());

        Mockito.when(groupService.getById(2L)).thenReturn(dto);

        mockMvc.perform(get("/api/groups/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group_id").value(2))
                .andExpect(jsonPath("$.group_name").value("Group B"))
                .andExpect(jsonPath("$.student_uuid").isArray());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getGroupById_shouldReturnForbidden_WhenRoleIsStudent() throws Exception {
        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getGroupById_shouldReturnBadRequest_WhenIdIsNull() throws Exception {
        mockMvc.perform(get("/api/groups/null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createGroup_shouldReturnBadRequest_WhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/groups")
                        .param("name", " "))
                .andExpect(status().isBadRequest());
    }
}
