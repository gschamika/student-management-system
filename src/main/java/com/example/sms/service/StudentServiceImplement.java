package com.example.sms.service;

import com.example.sms.excepton.ResourceNotFoundException;
import com.example.sms.excepton.SmsProjectException;
import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImplement implements StudentService{
    public static final String STUDENT_NOT_FOUND = "Student not found with ID: ";
    public static final String COURSE_NOT_FOUND = "Course not found with ID: ";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Student registerStudent(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new SmsProjectException("Error registering student", e);
        }
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + id));
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setAddress(studentDetails.getAddress());
            student.setPhoneNumber(studentDetails.getPhoneNumber());
            student.setEmail(studentDetails.getEmail());
            student.setNic(studentDetails.getNic());
            student.setGender(studentDetails.getGender());
            student.setDob(studentDetails.getDob());
            student.setSchoolOrUniversity(studentDetails.getSchoolOrUniversity());
            return studentRepository.save(student);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error updating student", e);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + id));
            studentRepository.delete(student);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error deleting student", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving students", e);
        }
    }

    @Override
    public Student getStudentById(Long id) {
        try {
            return studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error retrieving student", e);
        }
    }

    @Override
    public List<Student> searchStudentsByFirstName(String firstName) {
        try {
            return studentRepository.findByFirstNameContaining(firstName);
        } catch (Exception e) {
            throw new SmsProjectException("Error searching students by first name", e);
        }
    }

    @Override
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + studentId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + courseId));
            student.getCourses().add(course);
            studentRepository.save(student);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error enrolling student in course", e);
        }
    }

    @Override
    public void unenrollStudentFromCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + studentId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + courseId));
            student.getCourses().remove(course);
            studentRepository.save(student);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SmsProjectException("Error withdrawing student from course", e);
        }
    }
}
