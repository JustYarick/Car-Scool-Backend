package com.kubgtu.car_school.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.model.RequestStatusTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestRequest {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("time_to_call")
    private LocalDateTime timeToCall;
    @JsonProperty("status")
    private RequestStatusTypes status;
}
