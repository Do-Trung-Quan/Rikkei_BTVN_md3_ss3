package com.example.ss3.services;

import com.example.ss3.dto.PageResponse;
import com.example.ss3.dto.mapper.CourseMapper;
import com.example.ss3.dto.request.CourseCreateRequest;
import com.example.ss3.dto.response.CourseResponse;
import com.example.ss3.models.Course;
import com.example.ss3.models.CourseStatus;
import com.example.ss3.models.Instructor;
import com.example.ss3.repositories.CourseRepository;
import com.example.ss3.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         InstructorRepository instructorRepository,
                         CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseResponse> findAll() {
        List<Course> courseList = courseRepository.findAll();
        return courseMapper.toCourseResponseList(courseList);
    }

    public PageResponse<CourseResponse> getPageCourses(int page, int size, String sortBy, Sort.Direction direction, CourseStatus status){
        page = Math.max(page, 0);
        size = Math.max(size, 0);
        if(sortBy == null || sortBy.trim().isEmpty()) sortBy = "id";
        if(direction == null) direction = Sort.Direction.DESC;

        //Tạo sort và Pageable
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        //Lấy ra Page<Course> và map sang Page<CourseResponse>
        Page<Course> coursePage = courseRepository.findAllByStatus(status, pageable);
        Page<CourseResponse> courseResponsePage = coursePage.map(courseMapper::toCourseResponse);

        //map sang PageResponse<CourseResponse>
        return new PageResponse<CourseResponse>(
            courseResponsePage.getContent(),
            courseResponsePage.getNumber(),
            courseResponsePage.getSize(),
            courseResponsePage.getTotalElements(),
            courseResponsePage.getTotalPages(),
            courseResponsePage.isLast()
        );
    }

    public CourseResponse findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toCourseResponse(course);
    }

    public CourseResponse create(CourseCreateRequest req) {
        if (req == null) return null;
        if (req.getTitle() == null || req.getTitle().trim().isEmpty()) return null;
        if (req.getStatus() == null) return null;
        if (req.getInstructorId() == null || req.getInstructorId() <= 0) return null;

        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = new Course();
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);

        Course saved = courseRepository.save(course);
        return courseMapper.toCourseResponse(saved);
    }

    public boolean update(Long id, CourseCreateRequest req) {
        if (id == null || id <= 0 || req == null) return false;
        if (req.getTitle() == null || req.getTitle().trim().isEmpty()) return false;
        if (req.getStatus() == null) return false;

        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());

        if (req.getInstructorId() != null && req.getInstructorId() > 0) {
            Instructor instructor = instructorRepository.findById(req.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            course.setInstructor(instructor);
        }

        courseRepository.save(course);
        return true;
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0 || !courseRepository.existsById(id)) return false;

        courseRepository.deleteById(id);
        return true;
    }
}
