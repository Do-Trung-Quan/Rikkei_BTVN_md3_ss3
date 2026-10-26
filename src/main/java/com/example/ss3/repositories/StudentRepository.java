package com.example.ss3.repositories;

import com.example.ss3.models.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public StudentRepository() {
        students.add(new Student(1L, "Nguyen Van A", "a.nguyen@example.com"));
        students.add(new Student(2L, "Tran Thi B", "b.tran@example.com"));
    }

    public List<Student> findAll() {
        return students;
    }

    public Optional<Student> findById(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        for (Student student : students) {
            if (student.getId() != null && student.getId().equals(id)) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public Student add(Student student) {
        if (student == null || student.getId() == null || student.getId() <= 0) {
            return null;
        }
        if (findById(student.getId()).isPresent()) {
            return null;
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return null;
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return null;
        }
        students.add(student);
        return student;
    }

    public boolean update(Long id, Student student) {
        if (id == null || id <= 0 || student == null) return false;
        Student oldStudent = findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return false;
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return false;
        }

        oldStudent.setName(student.getName());
        oldStudent.setEmail(student.getEmail());
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0) return false;
        Student oldStudent = findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        return students.remove(oldStudent);
    }
}
