package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.ScheduleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByOrderByIdAsc(Pageable pageable);
    List<ScheduleEntity> findByGroup_Id(Long groupId);
    @Query("SELECT DISTINCT s FROM ScheduleEntity s WHERE s.group.id IN " +
            "(SELECT g.id FROM GroupEntity g JOIN g.studentsUuid su WHERE su = :studentUuid)")
    List<ScheduleEntity> findDistinctByStudentUuid(@Param("studentUuid") UUID studentUuid);
}