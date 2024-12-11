package com.example.sms.controller;

import com.example.sms.model.Subject;
import com.example.sms.service.SubjectService;
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
import static org.mockito.Mockito.times;

class SubjectControllerTest {
    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSubjectSuccessfully() {
        Subject subject = new Subject();
        when(subjectService.createSubject(subject)).thenReturn(subject);

        ResponseEntity<Subject> response = subjectController.createSubject(subject);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subject, response.getBody());
        verify(subjectService, times(1)).createSubject(subject);
    }

    @Test
    void updateSubjectSuccessfully() {
        Long subjectId = 1L;
        Subject subjectDetails = new Subject();
        Subject updatedSubject = new Subject();
        when(subjectService.updateSubject(subjectId, subjectDetails)).thenReturn(updatedSubject);

        ResponseEntity<Subject> response = subjectController.updateSubject(subjectId, subjectDetails);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSubject, response.getBody());
        verify(subjectService, times(1)).updateSubject(subjectId, subjectDetails);
    }

    @Test
    void deleteSubjectSuccessfully() {
        Long subjectId = 1L;

        ResponseEntity<Void> response = subjectController.deleteSubject(subjectId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subjectService, times(1)).deleteSubject(subjectId);
    }

    @Test
    void getSubjectByIdSuccessfully() {
        Long subjectId = 1L;
        Subject subject = new Subject();
        when(subjectService.getSubjectById(subjectId)).thenReturn(subject);

        ResponseEntity<Subject> response = subjectController.getSubjectById(subjectId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subject, response.getBody());
        verify(subjectService, times(1)).getSubjectById(subjectId);
    }

    @Test
    void getAllSubjectsSuccessfully() {
        List<Subject> subjects = List.of(new Subject(), new Subject());
        when(subjectService.getAllSubjects()).thenReturn(subjects);

        ResponseEntity<List<Subject>> response = subjectController.getAllSubjects();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjects, response.getBody());
        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void assignSubjectToCourseSuccessfully() {
        Long subjectId = 1L;
        Long courseId = 1L;

        ResponseEntity<Void> response = subjectController.assignSubjectToCourse(subjectId, courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(subjectService, times(1)).assignSubjectToCourse(subjectId, courseId);
    }

    @Test
    void removeSubjectFromCourseSuccessfully() {
        Long subjectId = 1L;
        Long courseId = 1L;

        ResponseEntity<Void> response = subjectController.removeSubjectFromCourse(subjectId, courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(subjectService, times(1)).removeSubjectFromCourse(subjectId, courseId);
    }
}
