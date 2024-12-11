package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.model.Subject;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.SubjectRepository;
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

class SubjectServiceImplementTest {
    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SubjectServiceImplement subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSubjectSuccessfully() {
        Subject subject = new Subject();
        when(subjectRepository.save(subject)).thenReturn(subject);

        Subject createdSubject = subjectService.createSubject(subject);

        assertNotNull(createdSubject);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void createSubjectThrowsSmsProjectException() {
        Subject subject = new Subject();
        when(subjectRepository.save(subject)).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.createSubject(subject));

        assertEquals("Error creating subject", exception.getMessage());
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void updateSubjectSuccessfully() {
        Long subjectId = 1L;
        Subject subjectDetails = new Subject();
        Subject existingSubject = new Subject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.save(existingSubject)).thenReturn(existingSubject);

        Subject updatedSubject = subjectService.updateSubject(subjectId, subjectDetails);

        assertNotNull(updatedSubject);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).save(existingSubject);
    }

    @Test
    void updateSubjectThrowsResourceNotFoundException() {
        Long subjectId = 1L;
        Subject subjectDetails = new Subject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.updateSubject(subjectId, subjectDetails));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void updateSubjectThrowsSmsProjectException() {
        Long subjectId = 1L;
        Subject subjectDetails = new Subject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        when(subjectRepository.save(any(Subject.class))).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.updateSubject(subjectId, subjectDetails));

        assertEquals("Error updating subject", exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void deleteSubjectSuccessfully() {
        Long subjectId = 1L;
        Subject existingSubject = new Subject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(existingSubject));

        subjectService.deleteSubject(subjectId);

        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).delete(existingSubject);
    }

    @Test
    void deleteSubjectThrowsResourceNotFoundException() {
        Long subjectId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.deleteSubject(subjectId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void deleteSubjectThrowsSmsProjectException() {
        Long subjectId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        doThrow(new RuntimeException()).when(subjectRepository).delete(any(Subject.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.deleteSubject(subjectId));

        assertEquals("Error deleting subject", exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).delete(any(Subject.class));
    }

    @Test
    void getSubjectByIdSuccessfully() {
        Long subjectId = 1L;
        Subject existingSubject = new Subject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(existingSubject));

        Subject subject = subjectService.getSubjectById(subjectId);

        assertNotNull(subject);
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void getSubjectByIdThrowsResourceNotFoundException() {
        Long subjectId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.getSubjectById(subjectId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void getSubjectByIdThrowsSmsProjectException() {
        Long subjectId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        doThrow(new RuntimeException()).when(subjectRepository).findById(subjectId);

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.getSubjectById(subjectId));

        assertEquals("Error retrieving subject", exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void getAllSubjectsSuccessfully() {
        when(subjectRepository.findAll()).thenReturn(List.of(new Subject(), new Subject()));

        List<Subject> subjects = subjectService.getAllSubjects();

        assertNotNull(subjects);
        assertEquals(2, subjects.size());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void getAllSubjectsThrowsSmsProjectException() {
        when(subjectRepository.findAll()).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.getAllSubjects());

        assertEquals("Error retrieving subjects", exception.getMessage());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void assignSubjectToCourseSuccessfully() {
        Long subjectId = 1L;
        Long courseId = 1L;
        Subject subject = new Subject();
        Course course = new Course();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        subjectService.assignSubjectToCourse(subjectId, courseId);

        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(subjectRepository, times(1)).save(subject);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void assignSubjectToCourseThrowsResourceNotFoundExceptionForSubject() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.assignSubjectToCourse(subjectId, courseId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void assignSubjectToCourseThrowsResourceNotFoundExceptionForCourse() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.assignSubjectToCourse(subjectId, courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void assignSubjectToCourseThrowsSmsProjectException() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(subjectRepository).save(any(Subject.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.assignSubjectToCourse(subjectId, courseId));

        assertEquals("Error assigning subject to course", exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void removeSubjectFromCourseSuccessfully() {
        Long subjectId = 1L;
        Long courseId = 1L;
        Subject subject = new Subject();
        Course course = new Course();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        subjectService.removeSubjectFromCourse(subjectId, courseId);

        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(subjectRepository, times(1)).save(subject);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void removeSubjectFromCourseThrowsResourceNotFoundExceptionForSubject() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.removeSubjectFromCourse(subjectId, courseId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void removeSubjectFromCourseThrowsResourceNotFoundExceptionForCourse() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> subjectService.removeSubjectFromCourse(subjectId, courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void removeSubjectFromCourseThrowsSmsProjectException() {
        Long subjectId = 1L;
        Long courseId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(subjectRepository).save(any(Subject.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> subjectService.removeSubjectFromCourse(subjectId, courseId));

        assertEquals("Error removing subject from course", exception.getMessage());
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }
}
