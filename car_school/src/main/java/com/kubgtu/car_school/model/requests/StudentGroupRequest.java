package com.kubgtu.car_school.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupRequest {

    @JsonProperty("user_uuid")
    @NonNull
    private UUID userUuid;
    @JsonProperty("group_id")
    @NonNull
    private Long groupId;
}
