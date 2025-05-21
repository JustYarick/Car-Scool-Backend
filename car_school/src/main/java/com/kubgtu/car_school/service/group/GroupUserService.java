package com.kubgtu.car_school.service.group;

import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.UserDTO;
import com.kubgtu.car_school.model.entity.GroupEntity;
import com.kubgtu.car_school.model.interfaces.IamApiService;
import com.kubgtu.car_school.repository.GroupRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class GroupUserService {

    private final GroupRepository groupRepository;
    private final IamApiService identityService;

    public List<UserDTO> getGroupUsers(@Valid @NotNull Long id) {
        GroupEntity group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));
        return group.getStudentsUuid()
                .stream()
                .map(uuid -> {
                    UserRepresentation user = identityService.getUserById(uuid)
                            .orElseThrow(() -> new UserNotFoundException("User not found" + uuid));
                    return UserDTO.convert(user);
                })
                .toList();
    }
}
