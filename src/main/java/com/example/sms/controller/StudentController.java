package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Validated
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping // Register new student
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.registerStudent(student));
    }

    @PutMapping("/{id}") // Edit student details by ID
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    @DeleteMapping("/{id}") // Delete student by ID
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping // List all students
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/search") // Search students by first name
    public ResponseEntity<List<Student>> searchStudentsByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(studentService.searchStudentsByFirstName(firstName));
    }

    @PutMapping("/{studentId}/enroll/{courseId}") // Enroll student in a course
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}/unenroll/{courseId}") // Unenroll student from a course
    public ResponseEntity<Void> unenrollStudentFromCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.unenrollStudentFromCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}