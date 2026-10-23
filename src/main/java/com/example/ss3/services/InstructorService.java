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

    public Instructor findById(int id){
        if(id <= 0) return null;
        return instructorRepository.findById(id);
    }

    public Instructor create(Instructor instructor){
        if(instructor == null) return null;
        if(instructor.getId() <= 0) return null;
        if(instructorRepository.findById(instructor.getId()) != null) return null;
        if(instructor.getName() == null || instructor.getName().trim().isEmpty()) return null;
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()) return null;

        return instructorRepository.add(instructor);
    }

    public boolean update(int id, Instructor instructor){
        // kiểm tra id ton tai
        if(id <= 0 || instructor == null || instructorRepository.findById(id) == null) return false;
        if(instructor.getName() == null || instructor.getName().trim().isEmpty()) return false;
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()) return false;

        //cap nhat
        return instructorRepository.update(id, instructor);
    }

    public boolean delete(int id){
        if(id <= 0 || instructorRepository.findById(id) == null) return false;

        return instructorRepository.delete(id);
    }
}
