package com.example.ss3.dto.mapper;

import com.example.ss3.dto.response.CourseInstructorResponse;
import com.example.ss3.dto.response.CourseResponse;
import com.example.ss3.models.Course;
import com.example.ss3.models.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "id", target = "instructorId")
    @Mapping(source = "name", target = "instructorName")
    @Mapping(source = "email", target = "instructorEmail")
    CourseInstructorResponse toCourseInstructorResponse(Instructor instructor);

    @Mapping(source = "instructor", target = "courseInstructorResponse")
    CourseResponse toCourseResponse(Course course);

    List<CourseResponse> toCourseResponseList(List<Course> courses);
}
