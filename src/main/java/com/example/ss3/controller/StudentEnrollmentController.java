package com.example.ss3.controller;

import com.example.ss3.dto.ApiResponse;
import com.example.ss3.models.StudentEnrollment;
import com.example.ss3.services.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<StudentEnrollment>> create(@RequestBody StudentEnrollment enrollment) {
        StudentEnrollment created = studentEnrollmentService.create(enrollment);
        if (created == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới enrollment"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới enrollment thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> update(@PathVariable Long id, @RequestBody StudentEnrollment enrollment) {
        try {
            boolean res = studentEnrollmentService.update(id, enrollment);
            if (res) return ResponseEntity.ok(ApiResponse.success("Cập nhật enrollment thành công", enrollment));
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
