package com.kubgtu.car_school.service_tests;

import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.service.KeycloakUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class KeycloakUserServiceTest {

    @Mock
    private Keycloak keycloak;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @Mock
    private UserResource userResource;

    @Mock
    private RoleMappingResource roleMappingResource;

    @Mock
    private RoleScopeResource roleScopeResource;

    @InjectMocks
    private KeycloakUserService keycloakUserService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(keycloakUserService, "realm", "test-realm");

        lenient().when(keycloak.realm("test-realm")).thenReturn(realmResource);
        lenient().when(realmResource.users()).thenReturn(usersResource);
        lenient().when(usersResource.get(anyString())).thenReturn(userResource);
    }

    @Test
    void getUserById_shouldReturnUser() {
        UUID userId = UUID.randomUUID();
        UserRepresentation user = new UserRepresentation();
        user.setId(userId.toString());

        when(userResource.toRepresentation()).thenReturn(user);

        Optional<UserRepresentation> result = keycloakUserService.getUserById(userId);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userId.toString());
    }

    @Test
    void getUserByIdWithRoles_shouldReturnUserWithRoles() {
        UUID userId = UUID.randomUUID();
        UserRepresentation user = new UserRepresentation();
        user.setId(userId.toString());

        when(userResource.toRepresentation()).thenReturn(user);
        when(userResource.roles()).thenReturn(roleMappingResource);
        when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
        when(roleScopeResource.listAll()).thenReturn(List.of(new RoleRepresentation("TEACHER", null, false)));

        Optional<UserRepresentation> result = keycloakUserService.getUserByIdWithRoles(userId);

        assertThat(result).isPresent();
        assertThat(result.get().getRealmRoles()).contains("TEACHER");
    }

    @Test
    void getUserRoles_shouldReturnRoleNames() {
        when(userResource.roles()).thenReturn(roleMappingResource);
        when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
        when(roleScopeResource.listAll()).thenReturn(List.of(
                new RoleRepresentation("STUDENT", null, false),
                new RoleRepresentation("TEACHER", null, false)
        ));

        List<String> roles = keycloakUserService.getUserRoles("some-user-id");

        assertThat(roles).containsExactlyInAnyOrder("STUDENT", "TEACHER");
    }

    @Test
    void hasRole_shouldReturnTrueWhenRoleExists() {
        UserRepresentation user = new UserRepresentation();
        user.setRealmRoles(List.of("STUDENT", "TEACHER"));

        boolean result = keycloakUserService.hasRole(user, "STUDENT");

        assertThat(result).isTrue();
    }

    @Test
    void isTeacher_shouldReturnTrue() {
        UUID userId = UUID.randomUUID();
        UserRepresentation user = new UserRepresentation();
        user.setId(userId.toString());
        user.setRealmRoles(List.of("TEACHER"));

        when(userResource.toRepresentation()).thenReturn(user);
        when(userResource.roles()).thenReturn(roleMappingResource);
        when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
        when(roleScopeResource.listAll()).thenReturn(List.of(new RoleRepresentation("TEACHER", null, false)));

        boolean result = keycloakUserService.isTeacher(userId);

        assertThat(result).isTrue();
    }

    @Test
    void isStudent_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userResource.toRepresentation()).thenReturn(null);

        assertThatThrownBy(() -> keycloakUserService.isStudent(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }
}
