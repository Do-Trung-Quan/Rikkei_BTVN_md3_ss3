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
        enrollments.add(new Enrollment(1L, "Nguyen Van A", 1L));
        enrollments.add(new Enrollment(2L, "Tran Thi B", 2L));
    }

    @Autowired
    public EnrollmentRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        enrollments.add(new Enrollment(1L, "Nguyen Van A", 1L));
        enrollments.add(new Enrollment(2L, "Tran Thi B", 2L));
    }

    public List<Enrollment> findAll(){
        return enrollments;
    }

    public Optional<Enrollment> findById(Long id){
        if(id == null || id <= 0) return Optional.empty();
        for(Enrollment enrollment : enrollments){
            if(enrollment.getId() != null && enrollment.getId().equals(id)){
                return Optional.of(enrollment);
            }
        }
        return Optional.empty();
    }

    public Enrollment add(Enrollment enrollment){
        if(enrollment == null || enrollment.getId() == null || enrollment.getId() <= 0){
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
        if(enrollment.getCourseId() == null || enrollment.getCourseId() <= 0){
            return null;
        }
        // Kiểm tra courseId có tồn tại trong hệ thống không
        if(courseRepository != null && courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return null;
        }

        enrollments.add(enrollment);
        return enrollment;
    }

    public boolean update(Long id, Enrollment enrollment){
        if(id == null || id <= 0 || enrollment == null) return false;
        Enrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));

        // Validate dữ liệu cập nhật
        if(enrollment.getStudentName() == null || enrollment.getStudentName().trim().isEmpty()){
            return false;
        }
        if(enrollment.getCourseId() == null || enrollment.getCourseId() <= 0){
            return false;
        }
        if(courseRepository != null && courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return false;
        }

        oldEnrollment.setStudentName(enrollment.getStudentName());
        oldEnrollment.setCourseId(enrollment.getCourseId());
        return true;
    }

    public boolean delete(Long id){
        if(id == null || id <= 0) return false;
        Enrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return enrollments.remove(oldEnrollment);
    }
}
