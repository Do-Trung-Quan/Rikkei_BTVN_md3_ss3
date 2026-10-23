package com.example.ss3.controller;

import com.example.ss3.models.Enrollment;
import com.example.ss3.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> list(){
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<Enrollment> create(
            @RequestBody Enrollment enrollment
    ){
        Enrollment created = enrollmentService.create(enrollment);
        if(created == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> update(
            @PathVariable int id,
            @RequestBody Enrollment enrollment
    ){
        if(enrollmentService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        boolean res = enrollmentService.update(id, enrollment);
        if(res) return ResponseEntity.ok(enrollment);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ){
        boolean res = enrollmentService.delete(id);
        if(res) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.notFound().build();
    }
}
