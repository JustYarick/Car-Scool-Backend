package com.kubgtu.car_school.controller.http.privateControllers;

import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.service.GroupService;
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

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> createGroup(@RequestParam String name ) {
        return ResponseEntity.ok(groupService.create(name));
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<GroupDTO> updateGroup(@RequestParam Long id, @RequestParam String name ) {
        return ResponseEntity.ok(groupService.update(id, name));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.delete(id));
    }
}
