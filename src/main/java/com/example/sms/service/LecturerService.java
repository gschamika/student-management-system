package com.example.sms.service;

import com.example.sms.model.Lecturer;

import java.util.List;

public interface LecturerService {
    Lecturer createLecturer(Lecturer lecturer);

    Lecturer updateLecturer(Long id, Lecturer lecturerDetails);

    void deleteLecturer(Long id);

    Lecturer getLecturerById(Long id);

    List<Lecturer> getAllLecturers();

    void assignLecturerToSubject(Long lecturerId, Long subjectId);

    void removeLecturerFromSubject(Long lecturerId, Long subjectId);
}
