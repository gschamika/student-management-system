package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceImplementTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentServiceImplement studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerStudentSuccessfully() {
        Student student = new Student();
        when(studentRepository.save(student)).thenReturn(student);

        Student registeredStudent = studentService.registerStudent(student);

        assertNotNull(registeredStudent);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void registerStudentThrowsSmsProjectException() {
        Student student = new Student();
        when(studentRepository.save(student)).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.registerStudent(student));

        assertEquals("Error registering student", exception.getMessage());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudentSuccessfully() {
        Long studentId = 1L;
        Student studentDetails = new Student();
        Student existingStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student updatedStudent = studentService.updateStudent(studentId, studentDetails);

        assertNotNull(updatedStudent);
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void updateStudentThrowsResourceNotFoundException() {
        Long studentId = 1L;
        Student studentDetails = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(studentId, studentDetails));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void updateStudentThrowsSmsProjectException() {
        Long studentId = 1L;
        Student studentDetails = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.updateStudent(studentId, studentDetails));

        assertEquals("Error updating student", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteStudentSuccessfully() {
        Long studentId = 1L;
        Student existingStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).delete(existingStudent);
    }

    @Test
    void deleteStudentThrowsResourceNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(studentId));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void deleteStudentThrowsSmsProjectException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        doThrow(new RuntimeException()).when(studentRepository).delete(any(Student.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.deleteStudent(studentId));

        assertEquals("Error deleting student", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).delete(any(Student.class));
    }

    @Test
    void getStudentByIdSuccessfully() {
        Long studentId = 1L;
        Student existingStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        Student student = studentService.getStudentById(studentId);

        assertNotNull(student);
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void getStudentByIdThrowsResourceNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(studentId));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void getStudentByIdThrowsSmsProjectException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        doThrow(new RuntimeException()).when(studentRepository).findById(studentId);

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.getStudentById(studentId));

        assertEquals("Error retrieving student", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void getAllStudentsSuccessfully() {
        when(studentRepository.findAll()).thenReturn(List.of(new Student(), new Student()));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAllStudentsThrowsSmsProjectException() {
        when(studentRepository.findAll()).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.getAllStudents());

        assertEquals("Error retrieving students", exception.getMessage());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void searchStudentsByFirstNameSuccessfully() {
        String firstName = "John";
        when(studentRepository.findByFirstNameContaining(firstName)).thenReturn(List.of(new Student(), new Student()));

        List<Student> students = studentService.searchStudentsByFirstName(firstName);

        assertNotNull(students);
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findByFirstNameContaining(firstName);
    }

    @Test
    void searchStudentsByFirstNameThrowsSmsProjectException() {
        String firstName = "John";
        when(studentRepository.findByFirstNameContaining(firstName)).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.searchStudentsByFirstName(firstName));

        assertEquals("Error searching students by first name", exception.getMessage());
        verify(studentRepository, times(1)).findByFirstNameContaining(firstName);
    }

    @Test
    void enrollStudentInCourseSuccessfully() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        Course course = new Course();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.enrollStudentInCourse(studentId, courseId);

        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void enrollStudentInCourseThrowsResourceNotFoundExceptionForStudent() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.enrollStudentInCourse(studentId, courseId));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void enrollStudentInCourseThrowsResourceNotFoundExceptionForCourse() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.enrollStudentInCourse(studentId, courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void enrollStudentInCourseThrowsSmsProjectException() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(studentRepository).save(any(Student.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.enrollStudentInCourse(studentId, courseId));

        assertEquals("Error enrolling student in course", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void unenrollStudentFromCourseSuccessfully() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        Course course = new Course();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.unenrollStudentFromCourse(studentId, courseId);

        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void unenrollStudentFromCourseThrowsResourceNotFoundExceptionForStudent() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.unenrollStudentFromCourse(studentId, courseId));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void unenrollStudentFromCourseThrowsResourceNotFoundExceptionForCourse() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.unenrollStudentFromCourse(studentId, courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void unenrollStudentFromCourseThrowsSmsProjectException() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(studentRepository).save(any(Student.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> studentService.unenrollStudentFromCourse(studentId, courseId));

        assertEquals("Error withdrawing student from course", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
}
