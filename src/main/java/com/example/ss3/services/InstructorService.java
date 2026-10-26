package com.example.ss3.services;

import com.example.ss3.dto.request.InstructorCreateRequest;
import com.example.ss3.models.Instructor;
import com.example.ss3.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    public Instructor create(InstructorCreateRequest req) {
        if (req == null) return null;
        if (req.getName() == null || req.getName().trim().isEmpty()) return null;
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) return null;

        Instructor instructor = new Instructor();
        instructor.setName(req.getName());
        instructor.setEmail(req.getEmail());
        return instructorRepository.save(instructor);
    }

    public boolean update(Long id, InstructorCreateRequest req) {
        if (id == null || id <= 0 || req == null) return false;
        if (req.getName() == null || req.getName().trim().isEmpty()) return false;
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) return false;

        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
        instructor.setName(req.getName());
        instructor.setEmail(req.getEmail());
        instructorRepository.save(instructor);
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || !instructorRepository.existsById(id)) return false;

        instructorRepository.deleteById(id);
        return true;
    }
}
