package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.ScheduleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByOrderByIdAsc(Pageable pageable);
}