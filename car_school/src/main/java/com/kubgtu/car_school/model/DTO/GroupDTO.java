package com.kubgtu.car_school.model.DTO;

import com.kubgtu.car_school.entity.GroupsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private long id;
    private String name;
    private List<UUID> studentUuids;

    public static GroupDTO convert(GroupsEntity group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getStudentsUuid()
        );
    }
}
