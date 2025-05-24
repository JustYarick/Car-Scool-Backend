package com.kubgtu.car_school.model.responces;

import com.kubgtu.car_school.model.DTO.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class SubjectResponse {
    List<SubjectDTO> data;

    public SubjectResponse(SubjectDTO data) {
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
