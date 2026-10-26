package com.example.ss3.controller;

import com.example.ss3.dto.ApiResponse;
import com.example.ss3.models.Student;
import com.example.ss3.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách student thành công", studentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Trả về student thành công", studentService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> create(@RequestBody Student student) {
        Student created = studentService.create(student);
        if (created == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới student"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới student thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> update(@PathVariable Long id, @RequestBody Student student) {
        try {
            boolean res = studentService.update(id, student);
            if (res) return ResponseEntity.ok(ApiResponse.success("Cập nhật student thành công", student));
            return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật student thất bại"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean res = studentService.delete(id);
            if (res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa student thành công"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy student"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
