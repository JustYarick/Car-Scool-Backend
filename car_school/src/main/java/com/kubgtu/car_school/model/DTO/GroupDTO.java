package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("group_id")
    private long id;
    @JsonProperty("group_name")
    private String name;
    @JsonProperty("student_uuid")
    private List<UUID> studentUuids;

    public static GroupDTO convert(GroupsEntity group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getStudentsUuid()
        );
    }
}
