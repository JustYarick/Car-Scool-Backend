package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.model.entity.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectDTO {
    @JsonProperty("subject_id")
    private Long subjectId;
    @JsonProperty("subject_name")
    private String subjectName;
    @JsonProperty("subject_description")
    private String subjectDescription;

    public static SubjectDTO convert(SubjectEntity subject) {
        return new SubjectDTO(
                subject.getSubjectId(),
                subject.getSubjectName(),
                subject.getSubjectDescription()
        );
    }
}
