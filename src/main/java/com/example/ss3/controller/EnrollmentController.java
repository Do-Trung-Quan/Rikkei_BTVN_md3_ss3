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
import com.example.ss3.models.Enrollment;
import com.example.ss3.services.EnrollmentService;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> list(){
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách enrollment thành công", enrollmentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> findById(
            @PathVariable int id
    ){
        try{
            return ResponseEntity.ok(ApiResponse.success("Trả về enrollment thành công", enrollmentService.findById(id)));
        }catch(Exception e){
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> create(
            @RequestBody Enrollment enrollment
    ){
        Enrollment created = enrollmentService.create(enrollment);
        if(created == null){
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới enrollment"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Thêm mới enrollment thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> update(
            @PathVariable int id,
            @RequestBody Enrollment enrollment
    ){
        try{
            boolean res = enrollmentService.update(id, enrollment);
            if(res) return ResponseEntity.ok(ApiResponse.success("Cập nhật enrollment thành công", enrollment));
            return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật enrollment thất bại"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable int id
    ){
        try{
            boolean res = enrollmentService.delete(id);
            if(res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa enrollment thành công"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy enrollment"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
