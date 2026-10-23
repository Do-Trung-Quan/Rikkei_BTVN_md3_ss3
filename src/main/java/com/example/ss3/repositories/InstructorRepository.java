package com.example.ss3.repositories;

import com.example.ss3.models.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorRepository {
    private List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository(){
        instructors.add(new Instructor(1, "quandt", "quandt@viettel.com.vn"));
        instructors.add(new Instructor(2, "dinhtq", "dinhtq@viettel.com.vn"));
    }

    public List<Instructor> findAll(){
        return instructors;
    }

    public Instructor findById(int id){
        if(id <= 0) return null;
        for(Instructor instructor : instructors){
            if(instructor.getId() == id){
                return instructor;
            }
        }
        return null;
    }

    public Instructor add(Instructor instructor){
        if(instructor == null || instructor.getId() <= 0){
            return null;
        }
        if(findById(instructor.getId()) != null){
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

    public boolean update(int id, Instructor instructor){
        if(id <= 0 || instructor == null) return false;
        Instructor oldInstructor = findById(id);
        if(oldInstructor == null) return false;

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

    public boolean delete(int id){
        if(id <= 0) return false;
        Instructor oldInstructor = findById(id);
        if(oldInstructor != null){
            return instructors.remove(oldInstructor);
        }
        return false;
    }
}
