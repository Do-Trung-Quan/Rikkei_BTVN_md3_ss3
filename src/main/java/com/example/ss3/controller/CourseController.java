package com.example.ss3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ss3.dto.ApiResponse;
import com.example.ss3.models.Course;
import com.example.ss3.services.CourseService;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> list(){
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách course thành công", courseService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> create(
            @RequestBody Course course
    ){
        Course created = courseService.create(course);
        if(created == null){
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới course"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới course thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> update(
            @PathVariable int id,
            @RequestBody Course course
    ){
        if(courseService.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy course"));
        }
        boolean res = courseService.update(id, course);
        if(res) return ResponseEntity.ok(ApiResponse.success("Cập nhật course thành công", course));
        return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật course thất bại"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable int id
    ){
        boolean res = courseService.delete(id);
        if(res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa course thành công"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy course"));
    }
}
