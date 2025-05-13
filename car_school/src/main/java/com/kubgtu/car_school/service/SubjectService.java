package com.kubgtu.car_school.service;

import com.kubgtu.car_school.model.entity.SubjectEntity;
import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.model.DTO.SubjectDTO;
import com.kubgtu.car_school.model.requests.CreateSubjectRequest;
import com.kubgtu.car_school.model.requests.UpdateSubjectRequest;
import com.kubgtu.car_school.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {

    private SubjectRepository subjectRepository;

    public List<SubjectDTO> getByPage(int page, int size) {
        return subjectRepository.findAllByOrderBySubjectIdAsc(PageRequest.of(page, size))
                .stream()
                .map(SubjectDTO::convert)
                .toList();
    }

    public SubjectEntity create(CreateSubjectRequest subjectRequest) {
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

    public SubjectDTO update(UpdateSubjectRequest subjectRequest) {
        SubjectEntity subject = subjectRepository.findById(subjectRequest.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        subject.setSubjectDescription(subjectRequest.getSubjectDescription());
        subject.setSubjectName(subjectRequest.getSubjectName());

        return SubjectDTO.convert(subjectRepository.save(subject));
    }
}
