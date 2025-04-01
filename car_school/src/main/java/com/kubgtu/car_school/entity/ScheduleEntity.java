package com.kubgtu.car_school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupsEntity group;

    private UUID teacherUUID;

    @Column(name = "lesson_date_start")
    private LocalDateTime lessonDateStart;

    @Column(name = "lesson_date_end")
    private LocalDateTime lessonDateEnd;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
}
