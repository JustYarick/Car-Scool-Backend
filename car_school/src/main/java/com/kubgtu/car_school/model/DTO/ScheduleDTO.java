package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.model.entity.ScheduleEntity;
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
        if (schedule == null) return null;

        Long groupId = schedule.getGroup() != null ? schedule.getGroup().getId() : null;
        UUID teacherUUID = schedule.getTeacherUUID();
        LocalDateTime lessonDateStart = schedule.getLessonDateStart();
        LocalDateTime lessonDateEnd = schedule.getLessonDateEnd();

        SubjectDTO subjectDTO = schedule.getSubject() != null
                ? SubjectDTO.convert(schedule.getSubject())
                : null;

        return ScheduleDTO.builder()
                .id(schedule.getId())
                .groupId(groupId)
                .teacherId(teacherUUID)
                .lessonDateStart(lessonDateStart)
                .lessonDateEnd(lessonDateEnd)
                .subjectId(subjectDTO)
                .build();
    }
}
