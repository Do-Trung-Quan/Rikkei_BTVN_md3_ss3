package com.example.ss3.repositories;

import com.example.ss3.models.Course;
import com.example.ss3.models.CourseStatus;
import com.example.ss3.models.Instructor;
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
        Instructor instructor1 = new Instructor(1L, "quandt", "quandt@viettel.com.vn");
        Instructor instructor2 = new Instructor(2L, "dinhtq", "dinhtq@viettel.com.vn");
        courses.add(new Course(1L, "course1", CourseStatus.ACTIVE, instructor1));
        courses.add(new Course(2L, "course2", CourseStatus.ACTIVE, instructor2));
    }

    @Autowired
    public CourseRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
        Instructor instructor1 = instructorRepository.findById(1L).orElseGet(() -> new Instructor(1L, "quandt", "quandt@viettel.com.vn"));
        Instructor instructor2 = instructorRepository.findById(2L).orElseGet(() -> new Instructor(2L, "dinhtq", "dinhtq@viettel.com.vn"));
        courses.add(new Course(1L, "course1", CourseStatus.ACTIVE, instructor1));
        courses.add(new Course(2L, "course2", CourseStatus.ACTIVE, instructor2));
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
        // Kiểm tra instructor hợp lệ
        if(course.getInstructor() == null || course.getInstructor().getId() == null || course.getInstructor().getId() <= 0){
            return null;
        }
        // Kiểm tra instructor có tồn tại trong hệ thống không
        if(instructorRepository != null) {
            Optional<Instructor> instructorOpt = instructorRepository.findById(course.getInstructor().getId());
            if(instructorOpt.isEmpty()){
                return null;
            }
            course.setInstructor(instructorOpt.get());
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
        if(course.getInstructor() == null || course.getInstructor().getId() == null || course.getInstructor().getId() <= 0){
            return false;
        }
        if(instructorRepository != null) {
            Optional<Instructor> instructorOpt = instructorRepository.findById(course.getInstructor().getId());
            if(instructorOpt.isEmpty()){
                return false;
            }
            oldCourse.setInstructor(instructorOpt.get());
        }

        oldCourse.setTitle(course.getTitle());
        oldCourse.setStatus(course.getStatus());
        return true;
    }

    public boolean delete(Long id){
        if(id == null || id <= 0) return false;
        Course oldCourse = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courses.remove(oldCourse);
    }
}
