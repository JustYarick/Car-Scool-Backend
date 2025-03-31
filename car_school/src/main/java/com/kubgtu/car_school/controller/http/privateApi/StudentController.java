package com.kubgtu.car_school.controller.http.privateApi;

import com.kubgtu.car_school.exception.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.StudentGroupRequest;
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
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    StudentService studentService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<StudentDTO> getStudentById(@RequestParam UUID uuid) {
        return ResponseEntity.ok(studentService.getStudentById(uuid).orElseThrow(() -> new UserNotFoundException("User not found")));
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
