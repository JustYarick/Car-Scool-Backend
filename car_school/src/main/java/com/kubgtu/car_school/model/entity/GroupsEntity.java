package com.kubgtu.car_school.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(
            name = "group_students",
            joinColumns = @JoinColumn(name = "group_id")
    )
    @Column(name = "student_uuid")
    private List<UUID> studentsUuid;
}