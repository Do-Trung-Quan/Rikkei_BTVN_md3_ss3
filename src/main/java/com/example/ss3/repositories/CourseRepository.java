package com.example.ss3.repositories;

import com.example.ss3.models.Course;
import com.example.ss3.models.CourseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private List<Course> courses = new ArrayList<>();
    private final InstructorRepository instructorRepository;

    public CourseRepository() {
        this.instructorRepository = null;
        courses.add(new Course(1L, "course1", CourseStatus.ACTIVE, 1L));
        courses.add(new Course(2L, "course2", CourseStatus.ACTIVE, 2L));
    }

    @Autowired
    public CourseRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
        courses.add(new Course(1L, "course1", CourseStatus.ACTIVE, 1L));
        courses.add(new Course(2L, "course2", CourseStatus.ACTIVE, 2L));
    }

    public List<Course> findAll(){
        return courses;
    }

    public Optional<Course> findById(Long id){
        if(id == null || id <= 0) return Optional.empty();
        for(Course course : courses){
            if(course.getId() != null && course.getId().equals(id)){
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    public Course add(Course course){
        if(course == null || course.getId() == null || course.getId() <= 0){
            return null;
        }
        // Kiểm tra trùng lặp ID
        if(findById(course.getId()).isPresent()){
            return null;
        }
        // Kiểm tra title không để trống
        if(course.getTitle() == null || course.getTitle().trim().isEmpty()){
            return null;
        }
        // Kiểm tra status
        if(course.getStatus() == null){
            return null;
        }
        // Kiểm tra instructorId hợp lệ
        if(course.getInstructorId() == null || course.getInstructorId() <= 0){
            return null;
        }
        // Kiểm tra instructorId có tồn tại trong hệ thống không
        if(instructorRepository != null && instructorRepository.findById(course.getInstructorId()).isEmpty()){
            return null;
        }

        courses.add(course);
        return course;
    }

    public boolean update(Long id, Course course){
        if(id == null || id <= 0 || course == null) return false;
        Course oldCourse = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        // Validate dữ liệu cập nhật
        if(course.getTitle() == null || course.getTitle().trim().isEmpty()){
            return false;
        }
        if(course.getStatus() == null){
            return false;
        }
        if(course.getInstructorId() == null || course.getInstructorId() <= 0){
            return false;
        }
        if(instructorRepository != null && instructorRepository.findById(course.getInstructorId()).isEmpty()){
            return false;
        }

        oldCourse.setTitle(course.getTitle());
        oldCourse.setStatus(course.getStatus());
        oldCourse.setInstructorId(course.getInstructorId());
        return true;
    }

    public boolean delete(Long id){
        if(id == null || id <= 0) return false;
        Course oldCourse = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courses.remove(oldCourse);
    }
}
