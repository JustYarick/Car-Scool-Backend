package com.kubgtu.car_school.model.responces;

import com.kubgtu.car_school.model.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class UserResponce {
    List<UserDTO> data;

    public UserResponce(UserDTO data) {
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
