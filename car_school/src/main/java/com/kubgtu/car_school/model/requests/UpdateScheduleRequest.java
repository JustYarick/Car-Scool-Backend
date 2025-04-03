package com.kubgtu.car_school.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleRequest {

    @JsonProperty("id")
    @NotNull
    private Long id;
    @JsonProperty("group_id")
    @NonNull
    private Long groupId;

    @JsonProperty("teacher_uuid")
    @NonNull
    private UUID teacherUUID;

    @JsonProperty("subject")
    @NonNull
    private Long subjectId;

    @JsonProperty("lesson_date_start")
    private LocalDateTime lessonDateStart;

    @JsonProperty("lesson_date_end")
    private LocalDateTime lessonDateEnd;
}