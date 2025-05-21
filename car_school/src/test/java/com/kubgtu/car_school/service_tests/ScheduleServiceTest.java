package com.kubgtu.car_school.service_tests;

import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.ScheduleDTO;
import com.kubgtu.car_school.model.entity.GroupEntity;
import com.kubgtu.car_school.model.entity.ScheduleEntity;
import com.kubgtu.car_school.model.entity.SubjectEntity;
import com.kubgtu.car_school.model.interfaces.IamApiService;
import com.kubgtu.car_school.model.requests.CreateScheduleRequest;
import com.kubgtu.car_school.model.requests.UpdateScheduleRequest;
import com.kubgtu.car_school.repository.GroupRepository;
import com.kubgtu.car_school.repository.ScheduleRepository;
import com.kubgtu.car_school.repository.SubjectRepository;
import com.kubgtu.car_school.service.ScheduleService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private IamApiService identityService;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private final UUID teacherUUID = UUID.randomUUID();
    private final GroupEntity group = new GroupEntity();
    private final SubjectEntity subject = new SubjectEntity();

    @Test
    void getByPage_shouldReturnScheduleDTOs() {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(1L);
        when(scheduleRepository.findAllByOrderByIdAsc(PageRequest.of(0, 5)))
                .thenReturn(List.of(entity));

        List<ScheduleDTO> result = scheduleService.getByPage(0, 5);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void getScheduleById_shouldReturnDTO_ifExists() {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(1L);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(entity));

        ScheduleDTO result = scheduleService.getScheduleById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getScheduleById_shouldThrow_ifNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getScheduleById(1L));
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        CreateScheduleRequest request = new CreateScheduleRequest();
        request.setTeacherUUID(teacherUUID);
        request.setGroupId(1L);
        request.setSubjectId(2L);
        request.setLessonDateStart(LocalDateTime.now());
        request.setLessonDateEnd(LocalDateTime.now().plusHours(1));

        when(identityService.isTeacher(teacherUUID)).thenReturn(true);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.save(any())).thenAnswer(inv -> {
            ScheduleEntity saved = inv.getArgument(0);
            saved.setId(99L);
            return saved;
        });

        ScheduleDTO result = scheduleService.create(request);

        assertThat(result.getId()).isEqualTo(99L);
        verify(scheduleRepository).save(any());
    }

    @Test
    void create_shouldThrow_ifNotTeacher() {
        CreateScheduleRequest request = new CreateScheduleRequest();
        request.setTeacherUUID(teacherUUID);
        when(identityService.isTeacher(teacherUUID)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> scheduleService.create(request));
    }

    @Test
    void create_shouldThrow_ifGroupNotFound() {
        CreateScheduleRequest request = new CreateScheduleRequest();
        request.setTeacherUUID(teacherUUID);
        request.setGroupId(1L);
        when(identityService.isTeacher(teacherUUID)).thenReturn(true);
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> scheduleService.create(request));
    }

    @Test
    void create_shouldThrow_ifSubjectNotFound() {
        CreateScheduleRequest request = new CreateScheduleRequest();
        request.setTeacherUUID(teacherUUID);
        request.setGroupId(1L);
        request.setSubjectId(2L);
        when(identityService.isTeacher(teacherUUID)).thenReturn(true);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> scheduleService.create(request));
    }

    @Test
    void update_shouldUpdateAndReturnDTO() {
        UpdateScheduleRequest request = new UpdateScheduleRequest();
        request.setId(1L);
        request.setGroupId(1L);
        request.setTeacherUUID(teacherUUID);
        request.setSubjectId(2L);
        request.setLessonDateStart(LocalDateTime.now());
        request.setLessonDateEnd(LocalDateTime.now().plusHours(1));

        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(1L);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ScheduleDTO result = scheduleService.update(request);

        assertThat(result.getId()).isEqualTo(1L);
        verify(scheduleRepository).save(any());
    }

    @Test
    void update_shouldThrow_ifScheduleNotFound() {
        UpdateScheduleRequest request = new UpdateScheduleRequest();
        request.setId(1L);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> scheduleService.update(request));
    }

    @Test
    void delete_shouldRemove_ifExists() {
        when(scheduleRepository.existsById(1L)).thenReturn(true);

        scheduleService.delete(1L);

        verify(scheduleRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrow_ifNotExists() {
        when(scheduleRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> scheduleService.delete(1L));
    }
}
