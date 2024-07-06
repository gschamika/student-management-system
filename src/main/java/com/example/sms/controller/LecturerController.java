package com.example.sms.controller;

import com.example.sms.model.Lecturer;
import com.example.sms.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturer")
@Validated
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @PostMapping // create lecturer
    public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer) {
        return ResponseEntity.ok(lecturerService.createLecturer(lecturer));
    }

    @PutMapping("/{id}") // update lecturer by id
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable Long id, @RequestBody Lecturer lecturerDetails) {
        return ResponseEntity.ok(lecturerService.updateLecturer(id, lecturerDetails));
    }

    @DeleteMapping("/{id}") // delete lecturer by id
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        lecturerService.deleteLecturer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable Long id) {
        Lecturer lecturer = lecturerService.getLecturerById(id);
        return ResponseEntity.ok(lecturer);
    }

    @GetMapping // get all lecturers
    public ResponseEntity<List<Lecturer>> getAllLecturers() {
        return ResponseEntity.ok(lecturerService.getAllLecturers());
    }

    @PostMapping("/{lecturerId}/assign/{subjectId}") // assign lecturer to subject
    public ResponseEntity<Void> assignLecturerToSubject(@PathVariable Long lecturerId, @PathVariable Long subjectId) {
        lecturerService.assignLecturerToSubject(lecturerId, subjectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lecturerId}/remove/{subjectId}") // remove lecturer from subject
    public ResponseEntity<Void> removeLecturerFromSubject(@PathVariable Long lecturerId, @PathVariable Long subjectId) {
        lecturerService.removeLecturerFromSubject(lecturerId, subjectId);
        return ResponseEntity.ok().build();
    }
}
