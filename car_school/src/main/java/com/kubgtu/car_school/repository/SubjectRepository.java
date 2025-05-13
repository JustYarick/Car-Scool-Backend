package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.SubjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findAllByOrderBySubjectIdAsc(Pageable pageable);
}
