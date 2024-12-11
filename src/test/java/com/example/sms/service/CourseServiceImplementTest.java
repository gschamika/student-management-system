package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CourseServiceImplementTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImplement courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourseSuccessfully() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void createCourseThrowsSmsProjectException() {
        Course course = new Course();
        when(courseRepository.save(course)).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> courseService.createCourse(course));

        assertEquals("Error creating course", exception.getMessage());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourseSuccessfully() {
        Long courseId = 1L;
        Course courseDetails = new Course();
        Course existingCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

        Course updatedCourse = courseService.updateCourse(courseId, courseDetails);

        assertNotNull(updatedCourse);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void updateCourseThrowsResourceNotFoundException() {
        Long courseId = 1L;
        Course courseDetails = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(courseId, courseDetails));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void updateCourseThrowsSmsProjectException() {
        Long courseId = 1L;
        Course courseDetails = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(courseRepository.save(any(Course.class))).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> courseService.updateCourse(courseId, courseDetails));

        assertEquals("Error updating course", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void deleteCourseSuccessfully() {
        Long courseId = 1L;
        Course existingCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).delete(existingCourse);
    }

    @Test
    void deleteCourseThrowsResourceNotFoundException() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void deleteCourseThrowsSmsProjectException() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(courseRepository).delete(any(Course.class));

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> courseService.deleteCourse(courseId));

        assertEquals("Error deleting course", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).delete(any(Course.class));
    }

    @Test
    void getCourseByIdSuccessfully() {
        Long courseId = 1L;
        Course existingCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

        Course course = courseService.getCourseById(courseId);

        assertNotNull(course);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourseByIdThrowsResourceNotFoundException() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.getCourseById(courseId));

        assertEquals("Course not found with ID: " + courseId, exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourseByIdThrowsSmsProjectException() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        doThrow(new RuntimeException()).when(courseRepository).findById(courseId);

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> courseService.getCourseById(courseId));

        assertEquals("Error retrieving course", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getAllCoursesSuccessfully() {
        when(courseRepository.findAll()).thenReturn(List.of(new Course(), new Course()));

        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getAllCoursesThrowsSmsProjectException() {
        when(courseRepository.findAll()).thenThrow(new RuntimeException());

        SmsProjectException exception = assertThrows(SmsProjectException.class, () -> courseService.getAllCourses());

        assertEquals("Error retrieving courses", exception.getMessage());
        verify(courseRepository, times(1)).findAll();
    }
}
