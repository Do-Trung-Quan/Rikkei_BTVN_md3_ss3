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
    public ResponseEntity<ApiResponse<List<Instructor>>> list(){
        return ResponseEntity.ok(ApiResponse.success("Trả về danh sách instructor thành công", instructorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> findById(
            @PathVariable Long id
    ){
        try{
            return ResponseEntity.ok(ApiResponse.success("trả về instructor thành công", instructorService.findById(id)));
        }catch(Exception e){
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> create(
            @RequestBody Instructor instructor
    ){
        Instructor created = instructorService.create(instructor);
        if(created == null){
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi thêm mới instructor"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("thêm mới thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> update(
            @PathVariable Long id,
            @RequestBody Instructor instructor
    ){
        boolean res = instructorService.update(id, instructor);
        if(res) return ResponseEntity.ok(ApiResponse.success("Cập nhật instructor thành công", instructor));
        return ResponseEntity.badRequest().body(ApiResponse.error("Cập nhật instructor thất bại"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ){
        boolean res = instructorService.delete(id);
        if(res) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Xóa instructor thành công"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Không tìm thấy instructor"));
    }
}
