package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;

import java.security.InvalidParameterException;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    public static UserDTO convert(UserRepresentation user) {
        if(user == null) return null;
        if(user.getId() == null) throw new InvalidParameterException("user id is null, cannot be converted to UserDTO");
        return UserDTO.builder()
                .id(UUID.fromString(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
