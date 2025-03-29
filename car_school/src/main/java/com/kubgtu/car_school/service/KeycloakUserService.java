package com.kubgtu.car_school.service;

import com.kubgtu.car_school.model.DTO.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
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

    public Optional<StudentDTO> getStudentById(UUID userId) {
        UserRepresentation user = keycloak.realm(realm)
                .users()
                .get(userId.toString())
                .toRepresentation();

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(StudentDTO.convert(user));
    }

    public List<StudentDTO> getAllStudents() {
        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .list();

        return users.stream()
                .filter(user -> {
                    List<String> roles = keycloak.realm(realm)
                            .users()
                            .get(user.getId())
                            .roles()
                            .realmLevel()
                            .listAll()
                            .stream()
                            .map(RoleRepresentation::getName)
                            .toList();

                    return roles.contains("STUDENT");
                })
                .map(StudentDTO::convert)
                .toList();
    }
}
