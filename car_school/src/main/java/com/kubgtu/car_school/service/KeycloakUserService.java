package com.kubgtu.car_school.service;

import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class KeycloakUserService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public Optional<UserRepresentation> getUserById(UUID userId) {
        return Optional.ofNullable(keycloak.realm(realm)
                .users()
                .get(userId.toString())
                .toRepresentation());
    }

    public Optional<UserRepresentation> getUserByIdWithRoles(UUID userId) {
        UserRepresentation user = keycloak.realm(realm)
                .users()
                .get(userId.toString())
                .toRepresentation();

        if (user == null) return Optional.empty();

        List<String> roles = keycloak.realm(realm)
                .users()
                .get(userId.toString())
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .toList();

        user.setRealmRoles(roles);
        return Optional.of(user);
    }

    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm)
                .users()
                .list();
    }

    public List<UserRepresentation> getAllUsersWithRoles() {
        List<UserRepresentation> users = keycloak.realm(realm).users().list();

        return users.stream()
                .map(user -> {
                    UserRepresentation userWithRoles = new UserRepresentation();
                    BeanUtils.copyProperties(user, userWithRoles);
                    List<String> roles = keycloak.realm(realm)
                            .users()
                            .get(user.getId())
                            .roles()
                            .realmLevel()
                            .listAll()
                            .stream()
                            .map(RoleRepresentation::getName)
                            .toList();
                    userWithRoles.setRealmRoles(roles);
                    return userWithRoles;
                })
                .toList();
    }

    public List<String> getUserRoles(String userId) {
        return keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
    }

    public boolean hasRole(UserRepresentation user, String role) {
        return user.getRealmRoles() != null && user.getRealmRoles().contains(role);
    }

    public boolean isTeacher(UUID userUuid) {
        Optional<UserRepresentation> optionalTeacher = getUserByIdWithRoles(userUuid);
        if(optionalTeacher.isEmpty()) throw new UserNotFoundException("User not found");
        return optionalTeacher.filter(userRepresentation -> hasRole(userRepresentation, "TEACHER")).isPresent();
    }

    public boolean isStudent(UUID userUuid) {
        Optional<UserRepresentation> optionalTeacher = getUserByIdWithRoles(userUuid);
        if(optionalTeacher.isEmpty()) throw new UserNotFoundException("User not found");
        return optionalTeacher.filter(userRepresentation -> hasRole(userRepresentation, "STUDENT")).isPresent();
    }
}
