package com.example.sms.controller;

import com.example.sms.model.Subject;
import com.example.sms.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
@Validated
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping // create subject
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @PutMapping("/{id}") // update subject by id
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        return ResponseEntity.ok(subjectService.updateSubject(id, subjectDetails));
    }

    @DeleteMapping("/{id}") // delete subject by id
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}") // get subject by id
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping // get all subjects
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PostMapping("/{subjectId}/assign/{courseId}") // assign subject to course
    public ResponseEntity<Void> assignSubjectToCourse(@PathVariable Long subjectId, @PathVariable Long courseId) {
        subjectService.assignSubjectToCourse(subjectId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subjectId}/remove/{courseId}") // remove subject from course
    public ResponseEntity<Void> removeSubjectFromCourse(@PathVariable Long subjectId, @PathVariable Long courseId) {
        subjectService.removeSubjectFromCourse(subjectId, courseId);
        return ResponseEntity.ok().build();
    }
}
