package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.model.Lecturer;
import com.example.sms.model.Subject;
import com.example.sms.repository.LecturerRepository;
import com.example.sms.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LecturerServiceImplement implements LecturerService{
    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Lecturer createLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    @Override
    public Lecturer updateLecturer(Long id, Lecturer lecturerDetails) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with ID: " + id));
        lecturer.setFirstName(lecturerDetails.getFirstName());
        lecturer.setLastName(lecturerDetails.getLastName());
        lecturer.setAddress(lecturerDetails.getAddress());
        lecturer.setPhoneNumber(lecturerDetails.getPhoneNumber());
        lecturer.setEmail(lecturerDetails.getEmail());
        lecturer.setNic(lecturerDetails.getNic());
        lecturer.setGender(lecturerDetails.getGender());
        lecturer.setDob(lecturerDetails.getDob());
        lecturer.setDegree(lecturerDetails.getDegree());

        return lecturerRepository.save(lecturer);
    }

    @Override
    public void deleteLecturer(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with ID: " + id));
        lecturerRepository.delete(lecturer);
    }

    @Override
    public Lecturer getLecturerById(Long id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with ID: " + id));
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    @Transactional
    public void assignLecturerToSubject(Long lecturerId, Long subjectId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with ID: " + lecturerId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        lecturer.getSubjects().add(subject);
        subject.getLecturers().add(lecturer);

        lecturerRepository.save(lecturer);
        subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void removeLecturerFromSubject(Long lecturerId, Long subjectId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with ID: " + lecturerId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        lecturer.getSubjects().remove(subject);
        subject.getLecturers().remove(lecturer);

        lecturerRepository.save(lecturer);
        subjectRepository.save(subject);
    }
}
