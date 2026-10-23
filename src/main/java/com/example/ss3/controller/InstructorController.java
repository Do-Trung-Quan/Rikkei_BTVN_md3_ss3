package com.example.ss3.controller;

import com.example.ss3.models.Instructor;
import com.example.ss3.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> list(){
        return ResponseEntity.ok(instructorService.findAll());
    }

    @PostMapping
    public ResponseEntity<Instructor> create(
            @RequestBody Instructor instructor
    ){
        Instructor created = instructorService.create(instructor);
        if(created == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> update(
            @PathVariable int id,
            @RequestBody Instructor instructor
    ){
        if(instructorService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        boolean res = instructorService.update(id, instructor);
        if(res) return ResponseEntity.ok(instructor);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ){
        boolean res = instructorService.delete(id);
        if(res) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.notFound().build();
    }
}
