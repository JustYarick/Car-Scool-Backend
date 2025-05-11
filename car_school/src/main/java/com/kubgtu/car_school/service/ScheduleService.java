package com.kubgtu.car_school.service;

import com.kubgtu.car_school.model.entity.ScheduleEntity;
import com.kubgtu.car_school.exception.ExceptionClass.GroupNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.exception.ExceptionClass.UserNotFoundException;
import com.kubgtu.car_school.model.DTO.ScheduleDTO;
import com.kubgtu.car_school.model.requests.CreateScheduleRequest;
import com.kubgtu.car_school.model.requests.UpdateScheduleRequest;
import com.kubgtu.car_school.repository.GroupRepository;
import com.kubgtu.car_school.repository.ScheduleRepository;
import com.kubgtu.car_school.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final KeycloakUserService keycloakUserService;
    private final SubjectRepository subjectRepository;

    public List<ScheduleDTO> getAllLessons() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleDTO::convert)
                .toList();
    }

    public ScheduleDTO getScheduleById(Long id) {
        return ScheduleDTO.convert(scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found")));
    }

    public ScheduleDTO create(CreateScheduleRequest request) {
        if(!keycloakUserService.isTeacher(request.getTeacherUUID())) throw new UserNotFoundException("Not a teacher");

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setLessonDateStart(request.getLessonDateStart());
        scheduleEntity.setLessonDateEnd(request.getLessonDateEnd());

        scheduleEntity.setGroup(groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group not found")));

        scheduleEntity.setTeacherUUID(request.getTeacherUUID());
        if(subjectRepository.findById(request.getSubjectId()).isEmpty()) throw new ResourceNotFoundException("Subject not found");
        scheduleEntity.setSubject(subjectRepository.findById(request.getSubjectId()).get());

        return ScheduleDTO.convert(scheduleRepository.save(scheduleEntity));
    }

    public ScheduleDTO update(UpdateScheduleRequest updateScheduleRequest) {
        ScheduleEntity schedule = scheduleRepository.findById(updateScheduleRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        schedule.setGroup(groupRepository.findById(updateScheduleRequest.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group not found"))
        );
        schedule.setTeacherUUID(updateScheduleRequest.getTeacherUUID());
        schedule.setSubject(subjectRepository.findById(updateScheduleRequest.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"))
        );

        if(updateScheduleRequest.getLessonDateStart() != null) schedule.setLessonDateStart(updateScheduleRequest.getLessonDateStart());
        if(updateScheduleRequest.getLessonDateEnd() != null) schedule.setLessonDateEnd(updateScheduleRequest.getLessonDateEnd());

        return ScheduleDTO.convert(scheduleRepository.save(schedule));
    }

    public void delete(Long id) {
        if(!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }
}
