package com.kubgtu.car_school.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {
    @JsonProperty("subject_name")
    @NonNull
    private String subjectName;
    @JsonProperty("subject_description")
    @NonNull
    private String subjectDescription;
}
