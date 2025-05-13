package com.kubgtu.car_school.controller.http;

import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.DTO.UserDTO;
import com.kubgtu.car_school.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/groups")
@Validated
public class GroupsController {

    private final GroupService groupService;

    @Operation(
            summary = "Получить все группы",
            description = "Доступно админам и преподавателям"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<GroupDTO>> getAllGroups(@RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "100") int size) {
        return ResponseEntity.ok(groupService.getByPage(page, size));
    }

    @Operation(
            summary = "Получить группу по id",
            description = "Доступно админам, преподавателям, студентам"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> getGroupById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @Operation(
            summary = "Получить пользователей входящих в группу",
            description = "Доступно админам, преподавателям"
    )
    @GetMapping("/{id}/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<UserDTO>> getUsersByGroupId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupUsers(id));
    }

    @Operation(
            summary = "Создать группу",
            description = "Доступно админам"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestParam String name ) {
        return ResponseEntity.ok(groupService.create(name));
    }

    @Operation(
            summary = "Обновить данные группы",
            description = "Доступно админам"
    )
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.update(groupDTO));
    }

    @Operation(
            summary = "Удалить группу",
            description = "Доступно админам"
    )
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGroup(@Valid @RequestParam Long id) {
        return ResponseEntity.ok(groupService.delete(id));
    }
}
