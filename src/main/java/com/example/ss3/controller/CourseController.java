package com.example.ss3.controller;

import java.util.List;

import com.example.ss3.dto.PageResponse;
import com.example.ss3.dto.request.CourseCreateRequest;
import com.example.ss3.dto.response.CourseResponse;
import com.example.ss3.dto.response.CourseResponseV2;
import com.example.ss3.models.CourseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ss3.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseV2>>> list(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam (value = "sortBy", required = false) String sortBy,
            @RequestParam (value = "direction", required = false) Sort.Direction direction,
            @RequestParam (value = "status", required = false) CourseStatus status
            ) {
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách course thành công", courseService.getPageCourses(page, size, sortBy, direction, status)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Trả về course thành công", courseService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> create(@RequestBody CourseCreateRequest request) {
        try {
            CourseResponse created = courseService.create(request);
            if (created == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới course"));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới course thành công", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(
            @PathVariable Long id,
            @RequestBody CourseCreateRequest request
    ) {
        try {
            boolean res = courseService.update(id, request);
            if (res) {
                CourseResponse updated = courseService.findById(id);
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
