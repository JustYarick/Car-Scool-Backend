package com.kubgtu.car_school.model.responces;

import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class RequestResponse {
    List<UserRequestDTO> data;

    public RequestResponse(UserRequestDTO data) {
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
