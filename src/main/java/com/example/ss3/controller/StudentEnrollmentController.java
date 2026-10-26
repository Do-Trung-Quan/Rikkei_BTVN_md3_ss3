package com.example.ss3.controller;

import java.util.List;

import com.example.ss3.dto.request.StudentEnrollmentCreateRequest;
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
import com.example.ss3.models.StudentEnrollment;
import com.example.ss3.services.StudentEnrollmentService;

@RestController
@RequestMapping({"/api/v1/student-enrollments", "/api/v1/enrollments"})
public class StudentEnrollmentController {
    private final StudentEnrollmentService studentEnrollmentService;

    @Autowired
    public StudentEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentEnrollment>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách enrollment thành công", studentEnrollmentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Trả về enrollment thành công", studentEnrollmentService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentEnrollment>> create(@RequestBody StudentEnrollmentCreateRequest request) {
        try {
            StudentEnrollment created = studentEnrollmentService.create(request);
            if (created == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới enrollment"));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới enrollment thành công", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> update(
            @PathVariable Long id,
            @RequestBody StudentEnrollmentCreateRequest request
    ) {
        try {
            boolean res = studentEnrollmentService.update(id, request);
            if (res) {
                StudentEnrollment updated = studentEnrollmentService.findById(id);
                return ResponseEntity.ok(ApiResponse.success("Cập nhật enrollment thành công", updated));
            }
            return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật enrollment thất bại"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean res = studentEnrollmentService.delete(id);
            if (res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa enrollment thành công"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy enrollment"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
