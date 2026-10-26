package com.example.ss3.services;

import com.example.ss3.models.Course;
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

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course findById(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course findCourseById(Long id){
        return findById(id);
    }

    public Course create(Course course){
        // Kiểm tra đối tượng null
        if(course == null) return null;

        // Kiểm tra id hợp lệ (> 0)
        if(course.getId() == null || course.getId() <= 0) return null;

        // Kiểm tra id chưa tồn tại
        if(courseRepository.findById(course.getId()).isPresent()){
            return null;
        }

        // Kiểm tra title không được để trống
        if(course.getTitle() == null || course.getTitle().trim().isEmpty()){
            return null;
        }

        // Kiểm tra status
        if(course.getStatus() == null){
            return null;
        }

        // Kiểm tra instructorId hợp lệ (> 0)
        if(course.getInstructorId() == null || course.getInstructorId() <= 0){
            return null;
        }

        // Kiểm tra instructorId phải tồn tại trong InstructorRepository
        if(instructorRepository.findById(course.getInstructorId()).isEmpty()){
            return null;
        }

        return courseRepository.add(course);
    }

    public boolean update(Long id, Course course){
        // Kiểm tra id và đối tượng null
        if(id == null || id <= 0 || course == null || courseRepository.findById(id).isEmpty()) return false;

        // Kiểm tra title không được để trống
        if(course.getTitle() == null || course.getTitle().trim().isEmpty()){
            return false;
        }

        // Kiểm tra status
        if(course.getStatus() == null){
            return false;
        }

        // Kiểm tra instructorId hợp lệ (> 0)
        if(course.getInstructorId() == null || course.getInstructorId() <= 0){
            return false;
        }

        // Kiểm tra instructorId phải tồn tại trong InstructorRepository
        if(instructorRepository.findById(course.getInstructorId()).isEmpty()){
            return false;
        }

        // Cập nhật
        return courseRepository.update(id, course);
    }

    public boolean delete(Long id){
        // Kiểm tra id hợp lệ và tồn tại
        if(id == null || id <= 0 || courseRepository.findById(id).isEmpty()) return false;

        return courseRepository.delete(id);
    }
}
