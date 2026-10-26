package com.example.ss3.repositories;

import com.example.ss3.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentEnrollmentRepository {
    private List<StudentEnrollment> enrollments = new ArrayList<>();
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentEnrollmentRepository() {
        this.studentRepository = null;
        this.courseRepository = null;
        Student student1 = new Student(1L, "Nguyen Van A", "a.nguyen@example.com");
        Student student2 = new Student(2L, "Tran Thi B", "b.tran@example.com");
        Instructor instructor1 = new Instructor(1L, "quandt", "quandt@viettel.com.vn");
        Course course1 = new Course(1L, "course1", CourseStatus.ACTIVE, instructor1);
        Course course2 = new Course(2L, "course2", CourseStatus.ACTIVE, instructor1);

        enrollments.add(new StudentEnrollment(1L, student1, course1));
        enrollments.add(new StudentEnrollment(2L, student2, course2));
    }

    @Autowired
    public StudentEnrollmentRepository(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;

        Student student1 = studentRepository.findById(1L).orElseGet(() -> new Student(1L, "Nguyen Van A", "a.nguyen@example.com"));
        Student student2 = studentRepository.findById(2L).orElseGet(() -> new Student(2L, "Tran Thi B", "b.tran@example.com"));
        Course course1 = courseRepository.findById(1L).orElseGet(() -> new Course(1L, "course1", CourseStatus.ACTIVE, null));
        Course course2 = courseRepository.findById(2L).orElseGet(() -> new Course(2L, "course2", CourseStatus.ACTIVE, null));

        enrollments.add(new StudentEnrollment(1L, student1, course1));
        enrollments.add(new StudentEnrollment(2L, student2, course2));
    }

    public List<StudentEnrollment> findAll() {
        return enrollments;
    }

    public Optional<StudentEnrollment> findById(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        for (StudentEnrollment enrollment : enrollments) {
            if (enrollment.getId() != null && enrollment.getId().equals(id)) {
                return Optional.of(enrollment);
            }
        }
        return Optional.empty();
    }

    public StudentEnrollment add(StudentEnrollment enrollment) {
        if (enrollment == null || enrollment.getId() == null || enrollment.getId() <= 0) {
            return null;
        }
        // Kiểm tra trùng lặp ID
        if (findById(enrollment.getId()).isPresent()) {
            return null;
        }
        // Kiểm tra student hợp lệ
        if (enrollment.getStudent() == null || enrollment.getStudent().getId() == null || enrollment.getStudent().getId() <= 0) {
            return null;
        }
        // Kiểm tra course hợp lệ
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() == null || enrollment.getCourse().getId() <= 0) {
            return null;
        }
        // Kiểm tra student có tồn tại không
        if (studentRepository != null) {
            Optional<Student> studentOpt = studentRepository.findById(enrollment.getStudent().getId());
            if (studentOpt.isEmpty()) return null;
            enrollment.setStudent(studentOpt.get());
        }
        // Kiểm tra course có tồn tại không
        if (courseRepository != null) {
            Optional<Course> courseOpt = courseRepository.findById(enrollment.getCourse().getId());
            if (courseOpt.isEmpty()) return null;
            enrollment.setCourse(courseOpt.get());
        }

        enrollments.add(enrollment);
        return enrollment;
    }

    public boolean update(Long id, StudentEnrollment enrollment) {
        if (id == null || id <= 0 || enrollment == null) return false;
        StudentEnrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (enrollment.getStudent() == null || enrollment.getStudent().getId() == null || enrollment.getStudent().getId() <= 0) {
            return false;
        }
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() == null || enrollment.getCourse().getId() <= 0) {
            return false;
        }

        if (studentRepository != null) {
            Optional<Student> studentOpt = studentRepository.findById(enrollment.getStudent().getId());
            if (studentOpt.isEmpty()) return false;
            oldEnrollment.setStudent(studentOpt.get());
        }
        if (courseRepository != null) {
            Optional<Course> courseOpt = courseRepository.findById(enrollment.getCourse().getId());
            if (courseOpt.isEmpty()) return false;
            oldEnrollment.setCourse(courseOpt.get());
        }

        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0) return false;
        StudentEnrollment oldEnrollment = findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return enrollments.remove(oldEnrollment);
    }
}
