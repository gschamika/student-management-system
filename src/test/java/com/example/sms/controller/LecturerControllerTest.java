package com.example.sms.controller;

import com.example.sms.model.Lecturer;
import com.example.sms.service.LecturerService;
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

class LecturerControllerTest {
    @Mock
    private LecturerService lecturerService;

    @InjectMocks
    private LecturerController lecturerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLecturerSuccessfully() {
        Lecturer lecturer = new Lecturer();
        when(lecturerService.createLecturer(lecturer)).thenReturn(lecturer);

        ResponseEntity<Lecturer> response = lecturerController.createLecturer(lecturer);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lecturer, response.getBody());
        verify(lecturerService, times(1)).createLecturer(lecturer);
    }

    @Test
    void updateLecturerSuccessfully() {
        Long lecturerId = 1L;
        Lecturer lecturerDetails = new Lecturer();
        Lecturer updatedLecturer = new Lecturer();
        when(lecturerService.updateLecturer(lecturerId, lecturerDetails)).thenReturn(updatedLecturer);

        ResponseEntity<Lecturer> response = lecturerController.updateLecturer(lecturerId, lecturerDetails);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedLecturer, response.getBody());
        verify(lecturerService, times(1)).updateLecturer(lecturerId, lecturerDetails);
    }

    @Test
    void deleteLecturerSuccessfully() {
        Long lecturerId = 1L;

        ResponseEntity<Void> response = lecturerController.deleteLecturer(lecturerId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(lecturerService, times(1)).deleteLecturer(lecturerId);
    }

    @Test
    void getLecturerByIdSuccessfully() {
        Long lecturerId = 1L;
        Lecturer lecturer = new Lecturer();
        when(lecturerService.getLecturerById(lecturerId)).thenReturn(lecturer);

        ResponseEntity<Lecturer> response = lecturerController.getLecturerById(lecturerId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lecturer, response.getBody());
        verify(lecturerService, times(1)).getLecturerById(lecturerId);
    }

    @Test
    void getAllLecturersSuccessfully() {
        List<Lecturer> lecturers = List.of(new Lecturer(), new Lecturer());
        when(lecturerService.getAllLecturers()).thenReturn(lecturers);

        ResponseEntity<List<Lecturer>> response = lecturerController.getAllLecturers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lecturers, response.getBody());
        verify(lecturerService, times(1)).getAllLecturers();
    }

    @Test
    void assignLecturerToSubjectSuccessfully() {
        Long lecturerId = 1L;
        Long subjectId = 1L;

        ResponseEntity<Void> response = lecturerController.assignLecturerToSubject(lecturerId, subjectId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(lecturerService, times(1)).assignLecturerToSubject(lecturerId, subjectId);
    }

    @Test
    void removeLecturerFromSubjectSuccessfully() {
        Long lecturerId = 1L;
        Long subjectId = 1L;

        ResponseEntity<Void> response = lecturerController.removeLecturerFromSubject(lecturerId, subjectId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(lecturerService, times(1)).removeLecturerFromSubject(lecturerId, subjectId);
    }
}
