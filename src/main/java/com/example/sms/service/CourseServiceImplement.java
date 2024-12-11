package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImplement implements CourseService{
    public static final String COURSE_NOT_FOUND = "Course not found with ID: ";

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        try {
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new SmsProjectException("Error creating course", e);
        }
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + id));
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setFee(courseDetails.getFee());
            course.setDuration(courseDetails.getDuration());
            course.setStartDate(courseDetails.getStartDate());
            course.setEndDate(courseDetails.getEndDate());
            return courseRepository.save(course);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error updating course", e);
        }
    }

    @Override
    public void deleteCourse(Long id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + id));
            courseRepository.delete(course);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error deleting course", e);
        }
    }

    @Override
    public Course getCourseById(Long id) {
        try {
            return courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving course", e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving courses", e);
        }
    }
}
