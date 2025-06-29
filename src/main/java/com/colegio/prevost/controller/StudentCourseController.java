package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.service.delegate.StudentCourseDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student-courses")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentCourseDeletage studentCourseDelegate;

    @GetMapping
    public ResponseEntity<List<StudentCourseDTO>> getAllStudentCourses() {
        List<StudentCourseDTO> courses = studentCourseDelegate.getAllStudentCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseDTO> getStudentCourseById(@PathVariable Long id) {
        StudentCourseDTO dto = studentCourseDelegate.getStudentCourseById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StudentCourseDTO> createStudentCourse(@RequestBody StudentCourseDTO studentCourse) {
        StudentCourseDTO created = studentCourseDelegate.createStudentCourse(studentCourse);
        return ResponseEntity
                .created(URI.create("/api/student-courses/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourseDTO> updateStudentCourse(
            @PathVariable Long id,
            @RequestBody StudentCourseDTO studentCourse) {

        StudentCourseDTO updated = studentCourseDelegate.updateStudentCourse(id, studentCourse);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentCourse(@PathVariable Long id) {
        studentCourseDelegate.deleteStudentCourse(id);
        return ResponseEntity.noContent().build();
    }
}
