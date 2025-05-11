package com.kubgtu.car_school.controller.http.privateControllers;

import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/groups")
public class GroupsController {

    GroupService groupService;

    @Operation(
            summary = "Получить все группы",
            description = "Доступно админам и учителям"
    )
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @Operation(
            summary = "Получить группу по id",
            description = "Доступно админам и учителям"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @Operation(
            summary = "Создать группу",
            description = "Доступно админам и учителям"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> createGroup(@RequestParam String name ) {
        return ResponseEntity.ok(groupService.create(name));
    }

    @Operation(
            summary = "Обновить данные группу",
            description = "Доступно админам и учителям"
    )
    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> updateGroup(@RequestParam Long id, @RequestParam String name ) {
        return ResponseEntity.ok(groupService.update(id, name));
    }

    @Operation(
            summary = "Удалить группу",
            description = "Доступно админам"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.delete(id));
    }
}
