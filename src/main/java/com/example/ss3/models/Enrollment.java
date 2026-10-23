package com.example.ss3.models;

public class Enrollment {
    private int id;
    private String studentName;
    private int courseId;

    public Enrollment() {
    }

    public Enrollment(int id, String studentName, int courseId) {
        this.id = id;
        this.studentName = studentName;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
