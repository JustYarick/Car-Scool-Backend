package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
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
