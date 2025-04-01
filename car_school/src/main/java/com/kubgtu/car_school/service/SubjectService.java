package com.kubgtu.car_school.service;

import com.kubgtu.car_school.entity.SubjectEntity;
import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.model.DTO.SubjectDTO;
import com.kubgtu.car_school.model.requests.SubjectRequest;
import com.kubgtu.car_school.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {

    private SubjectRepository subjectRepository;

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(SubjectDTO::convert).toList();
    }

    public SubjectEntity create(SubjectRequest subjectRequest) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setSubjectDescription(subjectRequest.getSubjectDescription());
        subjectEntity.setSubjectName(subjectRequest.getSubjectName());
        return subjectRepository.save(subjectEntity);
    }

    public void delete(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found");
        }
        subjectRepository.deleteById(id);
    }

    public SubjectDTO getSubjectById(Long id) {
        SubjectEntity subjectEntity = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return SubjectDTO.convert(subjectEntity);
    }
}
