package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.entity.ScheduleEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Long id;
    @JsonProperty("group_id")
    private Long groupId;
    @JsonProperty("teacher_id")
    private UUID teacherId;
    @JsonProperty("time_start")
    private LocalDateTime lessonDateStart;
    @JsonProperty("time_end")
    private LocalDateTime lessonDateEnd;
    @JsonProperty("subject_id")
    private SubjectDTO subjectId;

    public static ScheduleDTO convert(ScheduleEntity schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getGroup().getId(),
                schedule.getTeacherUUID(),
                schedule.getLessonDateStart(),
                schedule.getLessonDateEnd(),
                SubjectDTO.convert(schedule.getSubject())
        );
    }
}
