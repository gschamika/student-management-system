package com.example.sms.controller;

import com.example.sms.model.Course;
import com.example.sms.service.CourseService;
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

class CourseControllerTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourseSuccessfully() {
        Course course = new Course();
        when(courseService.createCourse(course)).thenReturn(course);

        ResponseEntity<Course> response = courseController.createCourse(course);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).createCourse(course);
    }

    @Test
    void updateCourseSuccessfully() {
        Long courseId = 1L;
        Course courseDetails = new Course();
        Course updatedCourse = new Course();
        when(courseService.updateCourse(courseId, courseDetails)).thenReturn(updatedCourse);

        ResponseEntity<Course> response = courseController.updateCourse(courseId, courseDetails);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCourse, response.getBody());
        verify(courseService, times(1)).updateCourse(courseId, courseDetails);
    }

    @Test
    void deleteCourseSuccessfully() {
        Long courseId = 1L;

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(courseId);
    }

    @Test
    void getCourseByIdSuccessfully() {
        Long courseId = 1L;
        Course course = new Course();
        when(courseService.getCourseById(courseId)).thenReturn(course);

        ResponseEntity<Course> response = courseController.getCourseById(courseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).getCourseById(courseId);
    }

    @Test
    void getAllCoursesSuccessfully() {
        List<Course> courses = List.of(new Course(), new Course());
        when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<List<Course>> response = courseController.getAllCourses();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).getAllCourses();
    }
}
