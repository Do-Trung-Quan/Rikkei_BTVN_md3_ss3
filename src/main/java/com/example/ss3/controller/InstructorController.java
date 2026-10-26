package com.example.ss3.controller;

import java.util.List;

import com.example.ss3.dto.request.InstructorCreateRequest;
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
import com.example.ss3.models.Instructor;
import com.example.ss3.services.InstructorService;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Instructor>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách instructor thành công", instructorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Trả về instructor thành công", instructorService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> create(@RequestBody InstructorCreateRequest request) {
        Instructor created = instructorService.create(request);
        if (created == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới instructor"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới instructor thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> update(
            @PathVariable Long id,
            @RequestBody InstructorCreateRequest request
    ) {
        try {
            boolean res = instructorService.update(id, request);
            if (res) {
                Instructor updated = instructorService.findById(id);
                return ResponseEntity.ok(ApiResponse.success("Cập nhật instructor thành công", updated));
            }
            return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật instructor thất bại"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean res = instructorService.delete(id);
            if (res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa instructor thành công"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy instructor"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
