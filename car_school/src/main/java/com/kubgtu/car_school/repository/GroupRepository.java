package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    List<GroupEntity> findAllByOrderByNameAsc(Pageable pageable);
    List<GroupEntity> findByStudentsUuidContaining(UUID studentUuid);
}
