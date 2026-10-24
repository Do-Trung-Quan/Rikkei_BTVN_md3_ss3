package com.example.ss3.repositories;

import com.example.ss3.models.Course;
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
        courses.add(new Course(1, "course1", "OK", 1));
        courses.add(new Course(2, "course2", "OK", 2));
    }

    @Autowired
    public CourseRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
        courses.add(new Course(1, "course1", "OK", 1));
        courses.add(new Course(2, "course2", "OK", 2));
    }

    public List<Course> findAll(){
        return courses;
    }

    public Optional<Course> findById(int id){
        if(id <= 0) return Optional.empty();
        for(Course course : courses){
            if(course.getId() == id){
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    public Course add(Course course){
        if(course == null || course.getId() <= 0){
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
        // Kiểm tra instructorId hợp lệ
        if(course.getInstructorId() <= 0){
            return null;
        }
        // Kiểm tra instructorId có tồn tại trong hệ thống không
        if(instructorRepository != null && instructorRepository.findById(course.getInstructorId()).isEmpty()){
            return null;
        }

        courses.add(course);
        return course;
    }

    public boolean update(int id, Course course){
        if(id <= 0 || course == null) return false;
        Course oldCourse = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        // Validate dữ liệu cập nhật
        if(course.getTitle() == null || course.getTitle().trim().isEmpty()){
            return false;
        }
        if(course.getInstructorId() <= 0){
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

    public boolean delete(int id){
        if(id <= 0) return false;
        Course oldCourse = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courses.remove(oldCourse);
    }
}
