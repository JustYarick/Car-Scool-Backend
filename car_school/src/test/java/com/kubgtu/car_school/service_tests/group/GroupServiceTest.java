package com.kubgtu.car_school.service_tests.group;


import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.entity.GroupEntity;
import com.kubgtu.car_school.repository.GroupRepository;
import com.kubgtu.car_school.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;


    @InjectMocks
    private GroupService groupService;


    private GroupEntity group;

    @BeforeEach
    void setup() {
        group = new GroupEntity();
        group.setId(1L);
        group.setName("Group A");
        group.setStudentsUuid(List.of(
                UUID.fromString("8ff5222c-2602-48d1-ad1a-c9cbf4152fba"),
                UUID.fromString("a387f9c4-20b9-41cd-80d9-0f6516997707")
                )
        );
    }

    @Test
    void getByPage_shouldReturnPagedGroups() {
        when(groupRepository.findAllByOrderByNameAsc(PageRequest.of(0, 2)))
                .thenReturn(List.of(group));

        List<GroupDTO> result = groupService.getByPage(0, 2);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Group A");
    }

    @Test
    void create_shouldSaveNewGroup() {
        GroupEntity toSave = new GroupEntity();
        toSave.setName("New Group");
        toSave.setStudentsUuid(new ArrayList<>());

        when(groupRepository.save(any())).thenReturn(group);

        GroupDTO created = groupService.create("New Group");

        assertThat(created.getName()).isEqualTo("Group A");
        verify(groupRepository).save(any(GroupEntity.class));
    }

    @Test
    void delete_existingGroup_shouldDelete() {
        when(groupRepository.existsById(1L)).thenReturn(true);

        groupService.delete(1L);

        verify(groupRepository).deleteById(1L);
    }

    @Test
    void delete_nonExistingGroup_shouldThrow() {
        when(groupRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> groupService.delete(1L))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessage("Group not found");
    }

    @Test
    void getById_shouldReturnGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        GroupDTO result = groupService.getById(1L);

        assertThat(result.getName()).isEqualTo("Group A");
    }

    @Test
    void getById_notFound_shouldThrow() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.getById(1L))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessage("Group not found");
    }

    @Test
    void update_existingGroup_shouldUpdate() {
        GroupDTO dto = new GroupDTO();
        dto.setId(1L);
        dto.setName("Updated Group");
        dto.setStudentUuids(List.of(UUID.fromString("8ff5222c-2602-48d1-ad1a-c9cbf4152fba")));

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupRepository.save(any())).thenReturn(group);

        GroupDTO updated = groupService.update(dto);

        assertThat(updated.getName()).isEqualTo("Updated Group");
        verify(groupRepository).save(any());
    }

    @Test
    void update_nonExistingGroup_shouldThrow() {
        GroupDTO dto = new GroupDTO();
        dto.setId(2L);

        when(groupRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.update(dto))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessage("Group not found");
    }
}