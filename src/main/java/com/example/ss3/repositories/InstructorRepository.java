package com.example.ss3.repositories;

import com.example.ss3.models.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InstructorRepository {
    private List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository(){
        instructors.add(new Instructor(1L, "quandt", "quandt@viettel.com.vn"));
        instructors.add(new Instructor(2L, "dinhtq", "dinhtq@viettel.com.vn"));
    }

    public List<Instructor> findAll(){
        return instructors;
    }

    public Optional<Instructor> findById(Long id){
        if(id == null || id <= 0) return Optional.empty();
        for(Instructor instructor : instructors){
            if(instructor.getId() != null && instructor.getId().equals(id)){
                return Optional.of(instructor);
            }
        }
        return Optional.empty();
    }

    public Instructor add(Instructor instructor){
        if(instructor == null || instructor.getId() == null || instructor.getId() <= 0){
            return null;
        }
        if(findById(instructor.getId()).isPresent()){
            return null;
        }
        if(instructor.getName() == null || instructor.getName().trim().isEmpty()){
            return null;
        }
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()){
            return null;
        }
        instructors.add(instructor);
        return instructor;
    }

    public boolean update(Long id, Instructor instructor){
        if(id == null || id <= 0 || instructor == null) return false;
        Instructor oldInstructor = findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));

        if(instructor.getName() == null || instructor.getName().trim().isEmpty()){
            return false;
        }
        if(instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()){
            return false;
        }

        oldInstructor.setName(instructor.getName());
        oldInstructor.setEmail(instructor.getEmail());
        return true;
    }

    public boolean delete(Long id){
        if(id == null || id <= 0) return false;
        Instructor oldInstructor = findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
        return instructors.remove(oldInstructor);
    }
}
