package com.example.ss3.services;

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

    public List<Instructor> findAll(){
        return instructorRepository.findAll();
    }

    public Instructor findById(Long id){
        return instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    public Instructor create(Instructor instructor){
        if(instructor == null) return null;
        if(instructor.getId() == null || instructor.getId() <= 0) return null;
        if(instructorRepository.findById(instructor.getId()).isPresent()) return null;
        if(instructor.getName() == null || instructor.getName().trim().isEmpty()) return null;
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()) return null;

        return instructorRepository.add(instructor);
    }

    public boolean update(Long id, Instructor instructor){
        // kiểm tra id ton tai
        if(id == null || id <= 0 || instructor == null || instructorRepository.findById(id).isEmpty()) return false;
        if(instructor.getName() == null || instructor.getName().trim().isEmpty()) return false;
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()) return false;

        //cap nhat
        return instructorRepository.update(id, instructor);
    }

    public boolean delete(Long id){
        if(id == null || id <= 0 || instructorRepository.findById(id).isEmpty()) return false;

        return instructorRepository.delete(id);
    }
}
