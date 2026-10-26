package com.example.ss3.dto.response;

public class CourseResponse {
    private Long id;
    private String title;
    private String status;
    private CourseInstructorResponse courseInstructorResponse;

    public CourseResponse(){}
    public CourseResponse(Long id, String title, String status, CourseInstructorResponse courseInstructorResponse) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.courseInstructorResponse = courseInstructorResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseInstructorResponse getCourseInstructorResponse() {
        return courseInstructorResponse;
    }

    public void setCourseInstructorResponse(CourseInstructorResponse courseInstructorResponse) {
        this.courseInstructorResponse = courseInstructorResponse;
    }
}
