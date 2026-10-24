package com.example.ss3.services;

import com.example.ss3.models.Enrollment;
import com.example.ss3.repositories.CourseRepository;
import com.example.ss3.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enrollment> findAll(){
        return enrollmentRepository.findAll();
    }

    public Enrollment findById(int id){
        return enrollmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    public Enrollment findEnrollmentById(int id){
        return findById(id);
    }

    public Enrollment create(Enrollment enrollment){
        // Kiểm tra đối tượng null
        if(enrollment == null) return null;

        // Kiểm tra id hợp lệ (> 0)
        if(enrollment.getId() <= 0) return null;

        // Kiểm tra id chưa tồn tại
        if(enrollmentRepository.findById(enrollment.getId()).isPresent()){
            return null;
        }

        // Kiểm tra tên học viên không được để trống
        if(enrollment.getStudentName() == null || enrollment.getStudentName().trim().isEmpty()){
            return null;
        }

        // Kiểm tra courseId hợp lệ (> 0)
        if(enrollment.getCourseId() <= 0){
            return null;
        }

        // Kiểm tra courseId phải tồn tại trong CourseRepository
        if(courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return null;
        }

        return enrollmentRepository.add(enrollment);
    }

    public boolean update(int id, Enrollment enrollment){
        // Kiểm tra id và đối tượng null
        if(id <= 0 || enrollment == null || enrollmentRepository.findById(id).isEmpty()) return false;

        // Kiểm tra tên học viên không được để trống
        if(enrollment.getStudentName() == null || enrollment.getStudentName().trim().isEmpty()){
            return false;
        }

        // Kiểm tra courseId hợp lệ (> 0)
        if(enrollment.getCourseId() <= 0){
            return false;
        }

        // Kiểm tra courseId phải tồn tại trong CourseRepository
        if(courseRepository.findById(enrollment.getCourseId()).isEmpty()){
            return false;
        }

        // Cập nhật
        return enrollmentRepository.update(id, enrollment);
    }

    public boolean delete(int id){
        // Kiểm tra id hợp lệ và tồn tại
        if(id <= 0 || enrollmentRepository.findById(id).isEmpty()) return false;

        return enrollmentRepository.delete(id);
    }
}
