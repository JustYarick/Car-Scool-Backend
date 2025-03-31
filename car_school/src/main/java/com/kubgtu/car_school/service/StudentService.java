package com.kubgtu.car_school.service;

import com.kubgtu.car_school.entity.GroupsEntity;
import com.kubgtu.car_school.exception.GroupNotFoundException;
import com.kubgtu.car_school.exception.UserAlreadyExistException;
import com.kubgtu.car_school.exception.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.StudentGroupRequest;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.DTO.StudentDTO;
import com.kubgtu.car_school.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {

    private final KeycloakUserService keycloakUserService;
    private final GroupRepository groupRepository;

    public Optional<StudentDTO> getStudentById(UUID studentId) {
        return keycloakUserService.getUserByIdWithRoles(studentId)
                .filter(user -> keycloakUserService.hasRole(user, "STUDENT"))
                .map(StudentDTO::convert);
    }

    public List<StudentDTO> getAllStudents() {
        return keycloakUserService.getAllUsersWithRoles().stream()
                .filter(user -> keycloakUserService.hasRole(user, "STUDENT"))
                .map(StudentDTO::convert)
                .toList();
    }

    public GroupDTO addStudentToGroup(StudentGroupRequest request) {
        GroupsEntity group = groupRepository.findById(request.getGroupId()).orElse(null);
        StudentDTO student = getStudentById(request.getUserUuid()).orElse(null);

        if (group == null) throw new GroupNotFoundException("Group not found");
        if (student == null) throw new UserNotFoundException("Student not found");
        if (group.getStudentsUuid().contains(student.getId())) throw new UserAlreadyExistException("User already exists");

        group.getStudentsUuid().add(student.getId());
        groupRepository.save(group);
        return GroupDTO.convert(group);
    }

    public GroupDTO deleteFromGroup(StudentGroupRequest request) {
        GroupsEntity group = groupRepository.findById(request.getGroupId()).orElse(null);
        StudentDTO student = getStudentById(request.getUserUuid()).orElse(null);

        if (group == null) throw new GroupNotFoundException("Group not found");
        if (student == null) throw new UserNotFoundException("Student not found");

        group.getStudentsUuid().remove(student.getId());
        groupRepository.save(group);
        return GroupDTO.convert(group);
    }
}
