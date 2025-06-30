package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<StudentCourseDTO> getStudentCourseById(@PathVariable String id) {
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
            @PathVariable String id,
            @RequestBody StudentCourseDTO studentCourse) {

        StudentCourseDTO updated = studentCourseDelegate.updateStudentCourse(id, studentCourse);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentCourse(@PathVariable String id) {
        studentCourseDelegate.deleteStudentCourse(id);
        return ResponseEntity.noContent().build();
    }
}
