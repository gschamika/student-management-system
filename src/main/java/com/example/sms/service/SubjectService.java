package com.example.sms.service;

import com.example.sms.model.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(Subject subject);

    Subject updateSubject(Long id, Subject subjectDetails);

    void deleteSubject(Long id);

    Subject getSubjectById(Long id);

    List<Subject> getAllSubjects();

    void assignSubjectToCourse(Long subjectId, Long courseId);

    void removeSubjectFromCourse(Long subjectId, Long courseId);
}
