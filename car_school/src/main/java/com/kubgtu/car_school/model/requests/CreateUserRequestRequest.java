package com.kubgtu.car_school.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestRequest {

    @JsonProperty("telephone")
    @NotEmpty(message = "Telephone cannot be empty")
    private String telephone;
    @JsonProperty("time_to_call")
    @NotNull(message = "Time to call cannot be null")
    private LocalDateTime timeToCall;
}
