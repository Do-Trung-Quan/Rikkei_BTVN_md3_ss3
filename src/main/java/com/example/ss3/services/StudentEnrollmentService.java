package com.example.ss3.services;

import com.example.ss3.dto.request.StudentEnrollmentCreateRequest;
import com.example.ss3.models.Course;
import com.example.ss3.models.Student;
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

    public StudentEnrollment create(StudentEnrollmentCreateRequest req) {
        if (req == null) return null;
        if (req.getStudentId() == null || req.getStudentId() <= 0) return null;
        if (req.getCourseId() == null || req.getCourseId() <= 0) return null;

        Student student = studentRepository.findById(req.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return studentEnrollmentRepository.save(enrollment);
    }

    public boolean update(Long id, StudentEnrollmentCreateRequest req) {
        if (id == null || id <= 0 || req == null) return false;

        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentEnrollment not found"));

        if (req.getStudentId() != null && req.getStudentId() > 0) {
            Student student = studentRepository.findById(req.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            enrollment.setStudent(student);
        }
        if (req.getCourseId() != null && req.getCourseId() > 0) {
            Course course = courseRepository.findById(req.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            enrollment.setCourse(course);
        }

        studentEnrollmentRepository.save(enrollment);
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || !studentEnrollmentRepository.existsById(id)) return false;

        studentEnrollmentRepository.deleteById(id);
        return true;
    }
}
