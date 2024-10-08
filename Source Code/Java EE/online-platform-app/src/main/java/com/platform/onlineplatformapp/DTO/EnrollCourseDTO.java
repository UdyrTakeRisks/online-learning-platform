package com.platform.onlineplatformapp.DTO;

public class EnrollCourseDTO {
    private String studentName;
    private String courseName;

    public EnrollCourseDTO() {

    }

    public EnrollCourseDTO(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
