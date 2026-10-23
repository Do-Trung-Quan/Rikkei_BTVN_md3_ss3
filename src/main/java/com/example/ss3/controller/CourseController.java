package com.example.ss3.controller;

import com.example.ss3.models.Course;
import com.example.ss3.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> list(){
        return ResponseEntity.ok(courseService.findAll());
    }

    @PostMapping
    public ResponseEntity<Course> create(
            @RequestBody Course course
    ){
        Course created = courseService.create(course);
        if(created == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(
            @PathVariable int id,
            @RequestBody Course course
    ){
        if(courseService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        boolean res = courseService.update(id, course);
        if(res) return ResponseEntity.ok(course);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ){
        boolean res = courseService.delete(id);
        if(res) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.notFound().build();
    }
}
