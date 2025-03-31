package com.kubgtu.car_school.repository;

import com.kubgtu.car_school.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupsEntity, Long> {

}
