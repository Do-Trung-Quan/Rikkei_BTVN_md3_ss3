package com.example.ss3.services;

import com.example.ss3.dto.request.StudentCreateRequest;
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

    public Student create(StudentCreateRequest req) {
        if (req == null) return null;
        if (req.getName() == null || req.getName().trim().isEmpty()) return null;
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) return null;

        Student student = new Student();
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        return studentRepository.save(student);
    }

    public boolean update(Long id, StudentCreateRequest req) {
        if (id == null || id <= 0 || req == null) return false;
        if (req.getName() == null || req.getName().trim().isEmpty()) return false;
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) return false;

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        studentRepository.save(student);
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || !studentRepository.existsById(id)) return false;

        studentRepository.deleteById(id);
        return true;
    }
}
