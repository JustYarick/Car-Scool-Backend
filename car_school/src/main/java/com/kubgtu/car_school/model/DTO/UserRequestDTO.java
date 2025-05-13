package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.model.entity.UserRequestEntity;
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
import java.util.function.Function;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user")
    private UserDTO user;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("time_to_call")
    private LocalDateTime timeToCall;
    @JsonProperty("status")
    private RequestStatusTypes status;

    public static UserRequestDTO convert(UserRequestEntity userRequestEntity, Function<UUID, Optional<UserRepresentation>> getUserFunction) {

        UserRepresentation user = getUserFunction.apply(userRequestEntity.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserRequestDTO(
                userRequestEntity.getId(),
                UserDTO.convert(user),
                userRequestEntity.getTelephone(),
                userRequestEntity.getTimeToCall(),
                userRequestEntity.getStatus()
        );
    }
}