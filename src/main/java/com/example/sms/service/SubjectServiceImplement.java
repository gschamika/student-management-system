package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.model.Subject;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImplement implements SubjectService{
    public static final String SUBJECT_NOT_FOUND = "Subject not found with ID: ";
    public static final String COURSE_NOT_FOUND = "Course not found with ID: ";

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Subject createSubject(Subject subject) {
        try {
            return subjectRepository.save(subject);
        } catch (Exception e) {
            throw new SmsProjectException("Error creating subject", e);
        }
    }

    @Override
    public Subject updateSubject(Long id, Subject subjectDetails) {
        try {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(SUBJECT_NOT_FOUND + id));
            subject.setName(subjectDetails.getName());
            subject.setDescription(subjectDetails.getDescription());
            // Update other fields as needed
            return subjectRepository.save(subject);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error updating subject", e);
        }
    }

    @Override
    public void deleteSubject(Long id) {
        try {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(SUBJECT_NOT_FOUND + id));
            subjectRepository.delete(subject);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error deleting subject", e);
        }
    }

    @Override
    public Subject getSubjectById(Long id) {
        try {
            return subjectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(SUBJECT_NOT_FOUND + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving subject", e);
        }
    }

    @Override
    public List<Subject> getAllSubjects() {
        try {
            return subjectRepository.findAll();
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving subjects", e);
        }
    }

    @Override
    @Transactional
    public void assignSubjectToCourse(Long subjectId, Long courseId) {
        try {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException(SUBJECT_NOT_FOUND + subjectId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + courseId));

            subject.getCourses().add(course);
            course.getSubjects().add(subject);

            subjectRepository.save(subject);
            courseRepository.save(course);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error assigning subject to course", e);
        }
    }

    @Override
    @Transactional
    public void removeSubjectFromCourse(Long subjectId, Long courseId) {
        try {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException(SUBJECT_NOT_FOUND + subjectId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + courseId));

            subject.getCourses().remove(course);
            course.getSubjects().remove(subject);

            subjectRepository.save(subject);
            courseRepository.save(course);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error removing subject from course", e);
        }
    }
}
