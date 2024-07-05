package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
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
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));
        subject.setName(subjectDetails.getName());
        subject.setDescription(subjectDetails.getDescription());
        // Update other fields as needed
        return subjectRepository.save(subject);
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));
        subjectRepository.delete(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    @Transactional
    public void assignSubjectToCourse(Long subjectId, Long courseId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        subject.getCourses().add(course);
        course.getSubjects().add(subject);

        subjectRepository.save(subject);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void removeSubjectFromCourse(Long subjectId, Long courseId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        subject.getCourses().remove(course);
        course.getSubjects().remove(subject);

        subjectRepository.save(subject);
        courseRepository.save(course);
    }
}
