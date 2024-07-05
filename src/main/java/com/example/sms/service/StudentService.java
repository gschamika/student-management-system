package com.example.sms.service;

import com.example.sms.model.Student;

import java.util.List;

public interface StudentService {
    Student registerStudent(Student student);

    Student updateStudent(Long id, Student studentDetails);

    void deleteStudent(Long id);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    List<Student> searchStudentsByFirstName(String firstName);

    void enrollStudentInCourse(Long studentId, Long courseId);

    void unenrollStudentFromCourse(Long studentId, Long courseId);
}
