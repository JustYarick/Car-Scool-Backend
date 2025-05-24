package com.kubgtu.car_school.controller.http;

import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.responces.GroupResponse;
import com.kubgtu.car_school.model.responces.UserResponce;
import com.kubgtu.car_school.service.group.GroupService;
import com.kubgtu.car_school.service.group.GroupUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/groups")
@Validated
public class GroupController {

    private final GroupService groupService;
    private final GroupUserService groupUserService;

    @Operation(
            summary = "Получить все группы",
            description = "Доступно админам и преподавателям"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupResponse> getAllGroups(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "100") int size) {
        return ResponseEntity.ok(GroupResponse.builder()
                .data(groupService.getByPage(page, size))
                .build()
        );
    }

    @Operation(
            summary = "Получить группу по id",
            description = "Доступно админам, преподавателям, студентам"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(new GroupResponse(groupService.getById(id)));
    }

    @Operation(
            summary = "Получить группу по id",
            description = "Доступно админам, преподавателям, студентам"
    )
    @GetMapping("/byStudent/{uuid}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupResponse> getGroupByStudent(@Valid @PathVariable UUID uuid) {
        return ResponseEntity.ok(GroupResponse.builder()
                .data(groupService.getByStudent(uuid))
                .build()
        );
    }

    @Operation(
            summary = "Получить пользователей входящих в группу",
            description = "Доступно админам, преподавателям"
    )
    @GetMapping("/{id}/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<UserResponce> getUsersByGroupId(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponce.builder()
                .data(groupUserService.getGroupUsers(id))
                .build()
        );
    }

    @Operation(
            summary = "Создать группу",
            description = "Доступно админам"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupResponse> create(@RequestParam String name ) {
        if (name == null || name.isBlank()) throw  new IllegalArgumentException("name is null or empty");
        return ResponseEntity.ok(new GroupResponse(groupService.create(name)));
    }

    @Operation(
            summary = "Обновить данные группы",
            description = "Доступно админам"
    )
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupResponse> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(new GroupResponse(groupService.update(groupDTO)));
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
