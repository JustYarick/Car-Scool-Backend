package com.kubgtu.car_school.model.interfaces;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;
import java.util.UUID;

public interface IamApiService {
    Optional<UserRepresentation> getUserById(UUID userId);
    Optional<UserRepresentation> getUserByIdWithRoles(UUID userId);
    boolean isTeacher(UUID userUuid);
    boolean isStudent(UUID userUuid);
    Optional<UserRepresentation> getUserByContext();
}
