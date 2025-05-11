package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long> {
    List<UserRequestEntity> findByUserId(UUID userId);

    List<UserRequestEntity> findAllByOrderByCreateRequestDateAsc(Pageable pageable);
}
