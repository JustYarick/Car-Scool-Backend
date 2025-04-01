package com.kubgtu.car_school.service;

import com.kubgtu.car_school.entity.GroupsEntity;
import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;



    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupDTO::convert)
                .toList();
    }

    public GroupDTO create(String name) {
        GroupsEntity group = new GroupsEntity();
        group.setName(name);
        group.setStudentsUuid(new ArrayList<>());
        group = groupRepository.save(group);

        return GroupDTO.convert(group);
    }

    public Void delete(long id) {

        if(groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        } else {
            throw new GroupNotFoundException("Group not found");
        }
        return null;
    }

    public GroupDTO getById(long id) {
        GroupsEntity group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));
        return GroupDTO.convert(group);
    }
}
