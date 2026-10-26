package com.example.ss3.services;

import com.example.ss3.models.Student;
import com.example.ss3.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student create(Student student) {
        if (student == null) return null;
        if (student.getId() == null || student.getId() <= 0) return null;
        if (studentRepository.findById(student.getId()).isPresent()) return null;
        if (student.getName() == null || student.getName().trim().isEmpty()) return null;
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) return null;

        return studentRepository.add(student);
    }

    public boolean update(Long id, Student student) {
        if (id == null || id <= 0 || student == null || studentRepository.findById(id).isEmpty()) return false;
        if (student.getName() == null || student.getName().trim().isEmpty()) return false;
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) return false;

        return studentRepository.update(id, student);
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || studentRepository.findById(id).isEmpty()) return false;

        return studentRepository.delete(id);
    }
}
