package com.example.ss3.services;

import com.example.ss3.models.StudentEnrollment;
import com.example.ss3.repositories.CourseRepository;
import com.example.ss3.repositories.StudentEnrollmentRepository;
import com.example.ss3.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentEnrollmentService {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentEnrollmentService(StudentEnrollmentRepository studentEnrollmentRepository,
                                  CourseRepository courseRepository,
                                  StudentRepository studentRepository) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentEnrollment> findAll() {
        return studentEnrollmentRepository.findAll();
    }

    public StudentEnrollment findById(Long id) {
        return studentEnrollmentRepository.findById(id).orElseThrow(() -> new RuntimeException("StudentEnrollment not found"));
    }

    public StudentEnrollment create(StudentEnrollment enrollment) {
        if (enrollment == null) return null;
        if (enrollment.getId() == null || enrollment.getId() <= 0) return null;
        if (studentEnrollmentRepository.findById(enrollment.getId()).isPresent()) return null;

        if (enrollment.getStudent() == null || enrollment.getStudent().getId() == null || enrollment.getStudent().getId() <= 0) {
            return null;
        }
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() == null || enrollment.getCourse().getId() <= 0) {
            return null;
        }

        if (studentRepository.findById(enrollment.getStudent().getId()).isEmpty()) {
            return null;
        }
        if (courseRepository.findById(enrollment.getCourse().getId()).isEmpty()) {
            return null;
        }

        return studentEnrollmentRepository.add(enrollment);
    }

    public boolean update(Long id, StudentEnrollment enrollment) {
        if (id == null || id <= 0 || enrollment == null || studentEnrollmentRepository.findById(id).isEmpty()) {
            return false;
        }

        if (enrollment.getStudent() == null || enrollment.getStudent().getId() == null || enrollment.getStudent().getId() <= 0) {
            return false;
        }
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() == null || enrollment.getCourse().getId() <= 0) {
            return false;
        }

        if (studentRepository.findById(enrollment.getStudent().getId()).isEmpty()) {
            return false;
        }
        if (courseRepository.findById(enrollment.getCourse().getId()).isEmpty()) {
            return false;
        }

        return studentEnrollmentRepository.update(id, enrollment);
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || studentEnrollmentRepository.findById(id).isEmpty()) {
            return false;
        }

        return studentEnrollmentRepository.delete(id);
    }
}
