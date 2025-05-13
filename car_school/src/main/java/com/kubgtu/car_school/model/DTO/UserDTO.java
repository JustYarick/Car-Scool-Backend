package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    public static UserDTO convert(UserRepresentation user) {
        return new UserDTO(
                UUID.fromString(user.getId()),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
