package com.example.ss3.dto.response;

public class CourseInstructorResponse {
    private Long  instructorId;
    private String instructorName;
    private String instructorEmail;

    public CourseInstructorResponse() {}
    public CourseInstructorResponse(Long instructorId, String instructorName, String instructorEmail) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }
}
