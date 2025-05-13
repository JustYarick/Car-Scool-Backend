package com.kubgtu.car_school;

import com.kubgtu.car_school.config.SecurityConfig;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.entity.GroupsEntity;
import com.kubgtu.car_school.repository.GroupRepository;
import com.kubgtu.car_school.service.GroupService;
import org.apache.catalina.Group;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class GroupsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllGroups_shouldReturnOkForAdmin() throws Exception {
        GroupsEntity group = new GroupsEntity();
        group.setId(10L);
        group.setName("123");
        group.setStudentsUuid(List.of());

        Mockito.when(groupRepository.findAllByOrderByNameAsc(PageRequest.of(0, 100)))
                .thenReturn(List.of(group));

        mockMvc.perform(get("/api/groups")
                        .param("page", "0")
                        .param("size", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].group_id").value(10))
                .andExpect(jsonPath("$[0].group_name").value("123"))
                .andExpect(jsonPath("$[0].student_uuid").isArray());
    }
}
