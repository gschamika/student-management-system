package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Lecturer;
import com.example.sms.model.Subject;
import com.example.sms.repository.LecturerRepository;
import com.example.sms.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerServiceImplement implements LecturerService{
    public static final String LECTURER_NOT_FOUND = "Lecturer not found with ID: ";

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Lecturer createLecturer(Lecturer lecturer) {
        try {
            return lecturerRepository.save(lecturer);
        } catch (Exception e) {
            throw new SmsProjectException("Error creating lecturer", e);
        }
    }

    @Override
    public Lecturer updateLecturer(Long id, Lecturer lecturerDetails) {
        try {
            Lecturer lecturer = lecturerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(LECTURER_NOT_FOUND + id));
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
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error updating lecturer", e);
        }
    }

    @Override
    public void deleteLecturer(Long id) {
        try {
            Lecturer lecturer = lecturerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(LECTURER_NOT_FOUND + id));
            lecturerRepository.delete(lecturer);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error deleting lecturer", e);
        }
    }

    @Override
    public Lecturer getLecturerById(Long id) {
        try {
            return lecturerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(LECTURER_NOT_FOUND + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving lecturer", e);
        }
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        try {
            return lecturerRepository.findAll();
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving lecturers", e);
        }
    }

    @Override
    @Transactional
    public void assignLecturerToSubject(Long lecturerId, Long subjectId) {
        try {
            Lecturer lecturer = lecturerRepository.findById(lecturerId)
                    .orElseThrow(() -> new ResourceNotFoundException(LECTURER_NOT_FOUND + lecturerId));
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

            lecturer.getSubjects().add(subject);
            subject.getLecturers().add(lecturer);

            lecturerRepository.save(lecturer);
            subjectRepository.save(subject);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error assigning lecturer to subject", e);
        }
    }

    @Override
    @Transactional
    public void removeLecturerFromSubject(Long lecturerId, Long subjectId) {
        try {
            Lecturer lecturer = lecturerRepository.findById(lecturerId)
                    .orElseThrow(() -> new ResourceNotFoundException(LECTURER_NOT_FOUND + lecturerId));
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

            lecturer.getSubjects().remove(subject);
            subject.getLecturers().remove(lecturer);

            lecturerRepository.save(lecturer);
            subjectRepository.save(subject);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error removing lecturer from subject", e);
        }
    }
}
