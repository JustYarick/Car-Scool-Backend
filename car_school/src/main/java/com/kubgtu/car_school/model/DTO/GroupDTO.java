package com.kubgtu.car_school.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubgtu.car_school.model.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDTO {
    @JsonProperty("group_id")
    private long id;
    @JsonProperty("group_name")
    private String name;
    @JsonProperty("student_uuid")
    private List<UUID> studentUuids;

    public static GroupDTO convert(GroupEntity group) {
        if (group == null) return null;

        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .studentUuids(group.getStudentsUuid())
                .build();
    }
}
