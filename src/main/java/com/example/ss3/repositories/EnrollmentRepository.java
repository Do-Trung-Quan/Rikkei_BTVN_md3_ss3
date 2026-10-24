package com.example.ss3.repositories;

import com.example.ss3.models.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {
    private List<Enrollment> enrollments = new ArrayList<>();
    private final CourseRepository courseRepository;

    public EnrollmentRepository() {
        this.courseRepository = null;
        enrollments.add(new Enrollment(1, "Nguyen Van A", 1));
        enrollments.add(new Enrollment(2, "Tran Thi B", 2));
    }

    @Autowired
    public EnrollmentRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        enrollments.add(new Enrollment(1, "Nguyen Van A", 1));
        enrollments.add(new Enrollment(2, "Tran Thi B", 2));
    }

    public List<Enrollment> findAll(){
        return enrollments;
    }

    public Optional<Enrollment> findById(int id){
        if(id <= 0) return Optional.empty();
        for(Enrollment enrollment : enrollments){
            if(enrollment.getId() == id){
                return Optional.of(enrollment);
            }
        }
        return Optional.empty();
    }

    public Enrollment add(Enrollment enrollment){
        if(enrollment == null || enrollment.getId() <= 0){
            return null;
        }
        // Kiểm tra trùng lặp ID
        if(findById(enrollment.getId()).isPresent()){
            return null;
        }
        // Kiểm tra tên học viên không để trống
        if(enrollment.getStudentName() == null || enrollment.getStudentName().trim().isEmpty()){
            return null;
        }
        // Kiểm tra courseId hợp lệ
        if(enrollment.getCourseId() <= 0){
            return null;
        }
        // Kiểm tra courseId có tồn tại trong hệ thống không
        if(courseRepository != null && courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return null;
        }

        enrollments.add(enrollment);
        return enrollment;
    }

    public boolean update(int id, Enrollment enrollment){
        if(id <= 0 || enrollment == null) return false;
        Enrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));

        // Validate dữ liệu cập nhật
        if(enrollment.getStudentName() == null || enrollment.getStudentName().trim().isEmpty()){
            return false;
        }
        if(enrollment.getCourseId() <= 0){
            return false;
        }
        if(courseRepository != null && courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return false;
        }

        oldEnrollment.setStudentName(enrollment.getStudentName());
        oldEnrollment.setCourseId(enrollment.getCourseId());
        return true;
    }

    public boolean delete(int id){
        if(id <= 0) return false;
        Enrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return enrollments.remove(oldEnrollment);
    }
}
