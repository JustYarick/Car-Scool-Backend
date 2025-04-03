package com.kubgtu.car_school.controller.http.privateControllers;

import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.requests.StudentGroupRequest;
import com.kubgtu.car_school.model.DTO.GroupDTO;
import com.kubgtu.car_school.model.DTO.StudentDTO;
import com.kubgtu.car_school.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {

    StudentService studentService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(studentService.getStudentById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @PostMapping("/group")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupDTO> addToGroup(@RequestBody StudentGroupRequest request) {
        return ResponseEntity.ok(studentService.addStudentToGroup(request));
    }

    @DeleteMapping("/group")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GroupDTO> deleteFromGroup(@RequestBody StudentGroupRequest request) {
        return ResponseEntity.ok(studentService.deleteFromGroup(request));
    }
}
