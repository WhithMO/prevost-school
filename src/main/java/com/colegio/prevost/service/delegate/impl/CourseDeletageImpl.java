package com.colegio.prevost.service.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.CourseDTO;
import com.colegio.prevost.model.Course;
import com.colegio.prevost.repository.CourseRepository;
import com.colegio.prevost.service.delegate.CourseDeletage;
import com.colegio.prevost.util.mapper.CourseMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseDeletageImpl implements CourseDeletage {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    @Override
    public CourseDTO getCourseById(String id) {
        Long convertedId = getConvertedId(id);
        return mapper.toCourseDTO(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : repository.findAll()) {
            courseDTOS.add(mapper.toCourseDTO(course));
        }
        return courseDTOS;
    }

    @Override
    public CourseDTO createCourse(CourseDTO course) {
        return mapper.toCourseDTO(repository.save(mapper.toEntity(course)));
    }

    @Override
    public CourseDTO updateCourse(String id, CourseDTO course) {
        CourseDTO existingCourse = getCourseById(id);
        if (existingCourse != null) {
            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setTeacher(course.getTeacher());
            existingCourse.setAssistantTeacher(course.getAssistantTeacher());
            existingCourse.setGrade(course.getGrade());
            existingCourse.setAcademicPeriod(course.getAcademicPeriod());
            return mapper.toCourseDTO(repository.save(mapper.toEntity(existingCourse)));
        }
        return null;
    }

    @Override
    public void deleteCourse(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
