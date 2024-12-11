package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerStudentSuccessfully() {
        Student student = new Student();
        when(studentService.registerStudent(student)).thenReturn(student);

        ResponseEntity<Student> response = studentController.registerStudent(student);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
        verify(studentService, times(1)).registerStudent(student);
    }

    @Test
    void updateStudentSuccessfully() {
        Long studentId = 1L;
        Student studentDetails = new Student();
        Student updatedStudent = new Student();
        when(studentService.updateStudent(studentId, studentDetails)).thenReturn(updatedStudent);

        ResponseEntity<Student> response = studentController.updateStudent(studentId, studentDetails);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
        verify(studentService, times(1)).updateStudent(studentId, studentDetails);
    }

    @Test
    void deleteStudentSuccessfully() {
        Long studentId = 1L;

        ResponseEntity<Void> response = studentController.deleteStudent(studentId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).deleteStudent(studentId);
    }

    @Test
    void getStudentByIdSuccessfully() {
        Long studentId = 1L;
        Student student = new Student();
        when(studentService.getStudentById(studentId)).thenReturn(student);

        ResponseEntity<Student> response = studentController.getStudentById(studentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
        verify(studentService, times(1)).getStudentById(studentId);
    }

    @Test
    void getAllStudentsSuccessfully() {
        List<Student> students = List.of(new Student(), new Student());
        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<Student>> response = studentController.getAllStudents();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void searchStudentsByFirstNameSuccessfully() {
        String firstName = "John";
        List<Student> students = List.of(new Student(), new Student());
        when(studentService.searchStudentsByFirstName(firstName)).thenReturn(students);

        ResponseEntity<List<Student>> response = studentController.searchStudentsByFirstName(firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(studentService, times(1)).searchStudentsByFirstName(firstName);
    }

    @Test
    void enrollStudentInCourseSuccessfully() {
        Long studentId = 1L;
        Long courseId = 1L;

        ResponseEntity<Void> response = studentController.enrollStudentInCourse(studentId, courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).enrollStudentInCourse(studentId, courseId);
    }

    @Test
    void unenrollStudentFromCourseSuccessfully() {
        Long studentId = 1L;
        Long courseId = 1L;

        ResponseEntity<Void> response = studentController.unenrollStudentFromCourse(studentId, courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).unenrollStudentFromCourse(studentId, courseId);
    }
}
