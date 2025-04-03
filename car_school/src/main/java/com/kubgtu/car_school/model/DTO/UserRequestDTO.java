package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.entity.UserRequestEntity;
import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.RequestStatusTypes;
import com.kubgtu.car_school.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("user_first_name")
    private String userFirstName;
    @JsonProperty("user_second_name")
    private String userSecondName;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("time_to_call")
    private LocalDateTime timeToCall;
    @JsonProperty("status")
    private RequestStatusTypes status;

    public static UserRequestDTO convert(UserRequestEntity userRequestEntity, KeycloakUserService keycloakUserService) {

        Optional<UserRepresentation> userOpt = keycloakUserService.getUserById(userRequestEntity.getUserId());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserRepresentation userRepresentation = userOpt.get();

        return new UserRequestDTO(
                userRequestEntity.getId(),
                userRequestEntity.getUserId(),
                userRepresentation.getFirstName(),
                userRepresentation.getLastName(),
                userRequestEntity.getTelephone(),
                userRequestEntity.getTimeToCall(),
                userRequestEntity.getStatus()
        );
    }
}