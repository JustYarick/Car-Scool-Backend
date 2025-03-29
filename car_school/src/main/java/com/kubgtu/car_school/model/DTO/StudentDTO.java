package com.kubgtu.car_school.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentDTO {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;

    public static StudentDTO convert(UserRepresentation user) {
        return new StudentDTO(
                UUID.fromString(user.getId()),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
