package com.kubgtu.car_school.service_tests.group;


import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.UserDTO;
import com.kubgtu.car_school.model.entity.GroupEntity;
import com.kubgtu.car_school.model.interfaces.IamApiService;
import com.kubgtu.car_school.repository.GroupRepository;
import com.kubgtu.car_school.service.group.GroupUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupUserServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private IamApiService identityService;

    @InjectMocks
    private GroupUserService groupUserService;

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
    void getGroupUsers_shouldReturnUsers() {
        UserRepresentation user1 = new UserRepresentation();
        user1.setId("8ff5222c-2602-48d1-ad1a-c9cbf4152fba");
        user1.setFirstName("John");
        user1.setLastName("Doe");

        UserRepresentation user2 = new UserRepresentation();
        user2.setId("a387f9c4-20b9-41cd-80d9-0f6516997707");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(identityService.getUserById(UUID.fromString("8ff5222c-2602-48d1-ad1a-c9cbf4152fba"))).thenReturn(Optional.of(user1));
        when(identityService.getUserById(UUID.fromString("a387f9c4-20b9-41cd-80d9-0f6516997707"))).thenReturn(Optional.of(user2));

        List<UserDTO> users = groupUserService.getGroupUsers(1L);

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getFirstName()).isEqualTo("John");
        assertThat(users.get(1).getLastName()).isEqualTo("Smith");
        assertThat(users.get(0).getId()).isEqualTo(UUID.fromString("8ff5222c-2602-48d1-ad1a-c9cbf4152fba"));
    }

    @Test
    void getGroupUsers_userNotFound_shouldThrow() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(identityService.getUserById(UUID.fromString("8ff5222c-2602-48d1-ad1a-c9cbf4152fba"))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupUserService.getGroupUsers(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void getGroupUsers_groupNotFound_shouldThrow() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupUserService.getGroupUsers(1L))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessage("Group not found");
    }
}