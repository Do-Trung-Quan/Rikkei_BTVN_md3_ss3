package com.example.ss3.controller;

import java.util.List;

import com.example.ss3.dto.request.CourseCreateRequest;
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
    public ResponseEntity<ApiResponse<List<Course>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách course thành công", courseService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Trả về course thành công", courseService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> create(@RequestBody CourseCreateRequest request) {
        try {
            Course created = courseService.create(request);
            if (created == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới course"));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới course thành công", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> update(
            @PathVariable Long id,
            @RequestBody CourseCreateRequest request
    ) {
        try {
            boolean res = courseService.update(id, request);
            if (res) {
                Course updated = courseService.findById(id);
                return ResponseEntity.ok(ApiResponse.success("Cập nhật course thành công", updated));
            }
            return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật course thất bại"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean res = courseService.delete(id);
            if (res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa course thành công"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy course"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
