package com.example.sms.service;

import com.example.sms.model.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);

    Course updateCourse(Long id, Course courseDetails);

    void deleteCourse(Long id);

    Course getCourseById(Long id);

    List<Course> getAllCourses();
}
