package com.kubgtu.car_school.service_tests;

import com.kubgtu.car_school.model.DTO.SubjectDTO;
import com.kubgtu.car_school.model.requests.CreateSubjectRequest;
import com.kubgtu.car_school.model.requests.UpdateSubjectRequest;
import com.kubgtu.car_school.service.SubjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.model.entity.SubjectEntity;
import com.kubgtu.car_school.repository.SubjectRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void getByPage_shouldReturnSubjectDTOs() {
        SubjectEntity entity = new SubjectEntity(1L, "Math", "Algebra");
        List<SubjectEntity> entities = List.of(entity);
        when(subjectRepository.findAllByOrderBySubjectIdAsc(PageRequest.of(0, 5))).thenReturn(entities);

        List<SubjectDTO> result = subjectService.getByPage(0, 5);

        assertEquals(1, result.size());
        assertEquals("Math", result.get(0).getSubjectName());
    }

    @Test
    void create_shouldSaveAndReturnSubjectEntity() {
        CreateSubjectRequest request = new CreateSubjectRequest("Physics", "Mechanics");
        SubjectEntity savedEntity = new SubjectEntity(1L, "Physics", "Mechanics");
        when(subjectRepository.save(any())).thenReturn(savedEntity);

        SubjectEntity result = subjectService.create(request);

        assertNotNull(result);
        assertEquals("Physics", result.getSubjectName());
        assertEquals("Mechanics", result.getSubjectDescription());
    }

    @Test
    void delete_shouldDeleteWhenSubjectExists() {
        Long id = 1L;
        when(subjectRepository.existsById(id)).thenReturn(true);

        subjectService.delete(id);

        verify(subjectRepository).deleteById(id);
    }

    @Test
    void delete_shouldThrowWhenSubjectNotFound() {
        Long id = 99L;
        when(subjectRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> subjectService.delete(id));
    }

    @Test
    void getSubjectById_shouldReturnDTOWhenFound() {
        SubjectEntity entity = new SubjectEntity(1L, "History", "World War II");
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(entity));

        SubjectDTO dto = subjectService.getSubjectById(1L);

        assertEquals("History", dto.getSubjectName());
    }

    @Test
    void getSubjectById_shouldThrowWhenNotFound() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.getSubjectById(1L));
    }

    @Test
    void update_shouldUpdateAndReturnDTO() {
        UpdateSubjectRequest request = new UpdateSubjectRequest(1L, "Biology", "Cell structure");
        SubjectEntity existing = new SubjectEntity(1L, "Old Name", "Old Desc");
        SubjectEntity updated = new SubjectEntity(1L, "Biology", "Cell structure");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(subjectRepository.save(any())).thenReturn(updated);

        SubjectDTO result = subjectService.update(request);

        assertEquals("Biology", result.getSubjectName());
        assertEquals("Cell structure", result.getSubjectDescription());
    }

    @Test
    void update_shouldThrowWhenSubjectNotFound() {
        UpdateSubjectRequest request = new UpdateSubjectRequest(1L, "Biology", "Cell structure");
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.update(request));
    }
}
