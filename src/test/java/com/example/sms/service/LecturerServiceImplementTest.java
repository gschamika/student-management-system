package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Lecturer;
import com.example.sms.model.Subject;
import com.example.sms.repository.LecturerRepository;
import com.example.sms.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LecturerServiceImplementTest {
    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private LecturerServiceImplement lecturerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLecturerSuccessfully() {
        Lecturer lecturer = new Lecturer();
        when(lecturerRepository.save(lecturer)).thenReturn(lecturer);

        Lecturer createdLecturer = lecturerService.createLecturer(lecturer);

        assertNotNull(createdLecturer);
        verify(lecturerRepository, times(1)).save(lecturer);
    }

    @Test
    void createLecturerThrowsSmsProjectException() {
        Lecturer lecturer = new Lecturer();
        when(lecturerRepository.save(lecturer)).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.createLecturer(lecturer));

        assertEquals("Error creating lecturer", exception.getMessage());
        verify(lecturerRepository, times(1)).save(lecturer);
    }

    @Test
    void updateLecturerSuccessfully() {
        Long lecturerId = 1L;
        Lecturer lecturerDetails = new Lecturer();
        Lecturer existingLecturer = new Lecturer();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(existingLecturer));
        when(lecturerRepository.save(existingLecturer)).thenReturn(existingLecturer);

        Lecturer updatedLecturer = lecturerService.updateLecturer(lecturerId, lecturerDetails);

        assertNotNull(updatedLecturer);
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(lecturerRepository, times(1)).save(existingLecturer);
    }

    @Test
    void updateLecturerThrowsResourceNotFoundException() {
        Long lecturerId = 1L;
        Lecturer lecturerDetails = new Lecturer();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.updateLecturer(lecturerId, lecturerDetails));

        assertEquals("Lecturer not found with ID: " + lecturerId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void updateLecturerThrowsSmsProjectException() {
        Long lecturerId = 1L;
        Lecturer lecturerDetails = new Lecturer();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        when(lecturerRepository.save(any(Lecturer.class))).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.updateLecturer(lecturerId, lecturerDetails));

        assertEquals("Error updating lecturer", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }

    @Test
    void deleteLecturerSuccessfully() {
        Long lecturerId = 1L;
        Lecturer existingLecturer = new Lecturer();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(existingLecturer));

        lecturerService.deleteLecturer(lecturerId);

        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(lecturerRepository, times(1)).delete(existingLecturer);
    }

    @Test
    void deleteLecturerThrowsResourceNotFoundException() {
        Long lecturerId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.deleteLecturer(lecturerId));

        assertEquals("Lecturer not found with ID: " + lecturerId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void deleteLecturerThrowsSmsProjectException() {
        Long lecturerId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        doThrow(new RuntimeException()).when(lecturerRepository).delete(any(Lecturer.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.deleteLecturer(lecturerId));

        assertEquals("Error deleting lecturer", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(lecturerRepository, times(1)).delete(any(Lecturer.class));
    }

    @Test
    void getLecturerByIdSuccessfully() {
        Long lecturerId = 1L;
        Lecturer existingLecturer = new Lecturer();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(existingLecturer));

        Lecturer lecturer = lecturerService.getLecturerById(lecturerId);

        assertNotNull(lecturer);
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void getLecturerByIdThrowsResourceNotFoundException() {
        Long lecturerId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.getLecturerById(lecturerId));

        assertEquals("Lecturer not found with ID: " + lecturerId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void getLecturerByIdThrowsSmsProjectException() {
        Long lecturerId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        doThrow(new RuntimeException()).when(lecturerRepository).findById(lecturerId);

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.getLecturerById(lecturerId));

        assertEquals("Error retrieving lecturer", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void getAllLecturersSuccessfully() {
        when(lecturerRepository.findAll()).thenReturn(List.of(new Lecturer(), new Lecturer()));

        List<Lecturer> lecturers = lecturerService.getAllLecturers();

        assertNotNull(lecturers);
        assertEquals(2, lecturers.size());
        verify(lecturerRepository, times(1)).findAll();
    }

    @Test
    void getAllLecturersThrowsSmsProjectException() {
        when(lecturerRepository.findAll()).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.getAllLecturers());

        assertEquals("Error retrieving lecturers", exception.getMessage());
        verify(lecturerRepository, times(1)).findAll();
    }

    @Test
    void assignLecturerToSubjectSuccessfully() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        Lecturer lecturer = new Lecturer();
        Subject subject = new Subject();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        lecturerService.assignLecturerToSubject(lecturerId, subjectId);

        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lecturerRepository, times(1)).save(lecturer);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void assignLecturerToSubjectThrowsResourceNotFoundExceptionForLecturer() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.assignLecturerToSubject(lecturerId, subjectId));

        assertEquals("Lecturer not found with ID: " + lecturerId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void assignLecturerToSubjectThrowsResourceNotFoundExceptionForSubject() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.assignLecturerToSubject(lecturerId, subjectId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void assignLecturerToSubjectThrowsSmsProjectException() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        doThrow(new RuntimeException()).when(lecturerRepository).save(any(Lecturer.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.assignLecturerToSubject(lecturerId, subjectId));

        assertEquals("Error assigning lecturer to subject", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }

    @Test
    void removeLecturerFromSubjectSuccessfully() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        Lecturer lecturer = new Lecturer();
        Subject subject = new Subject();
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        lecturerService.removeLecturerFromSubject(lecturerId, subjectId);

        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lecturerRepository, times(1)).save(lecturer);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void removeLecturerFromSubjectThrowsResourceNotFoundExceptionForLecturer() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.removeLecturerFromSubject(lecturerId, subjectId));

        assertEquals("Lecturer not found with ID: " + lecturerId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
    }

    @Test
    void removeLecturerFromSubjectThrowsResourceNotFoundExceptionForSubject() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> lecturerService.removeLecturerFromSubject(lecturerId, subjectId));

        assertEquals("Subject not found with ID: " + subjectId, exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void removeLecturerFromSubjectThrowsSmsProjectException() {
        Long lecturerId = 1L;
        Long subjectId = 1L;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(new Lecturer()));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(new Subject()));
        doThrow(new RuntimeException()).when(lecturerRepository).save(any(Lecturer.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> lecturerService.removeLecturerFromSubject(lecturerId, subjectId));

        assertEquals("Error removing lecturer from subject", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(lecturerId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }
}
