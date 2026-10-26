package com.example.ss3.services;

import com.example.ss3.dto.request.CourseCreateRequest;
import com.example.ss3.models.Course;
import com.example.ss3.models.Instructor;
import com.example.ss3.repositories.CourseRepository;
import com.example.ss3.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course create(CourseCreateRequest req) {
        if (req == null) return null;
        if (req.getTitle() == null || req.getTitle().trim().isEmpty()) return null;
        if (req.getStatus() == null) return null;
        if (req.getInstructorId() == null || req.getInstructorId() <= 0) return null;

        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = new Course();
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);

        return courseRepository.save(course);
    }

    public boolean update(Long id, CourseCreateRequest req) {
        if (id == null || id <= 0 || req == null) return false;
        if (req.getTitle() == null || req.getTitle().trim().isEmpty()) return false;
        if (req.getStatus() == null) return false;

        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());

        if (req.getInstructorId() != null && req.getInstructorId() > 0) {
            Instructor instructor = instructorRepository.findById(req.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            course.setInstructor(instructor);
        }

        courseRepository.save(course);
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || !courseRepository.existsById(id)) return false;

        courseRepository.deleteById(id);
        return true;
    }
}
