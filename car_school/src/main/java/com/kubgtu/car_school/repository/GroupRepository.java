package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.model.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupsEntity, Long> {

    List<GroupsEntity> findAllByOrderByNameAsc(Pageable pageable);
}
