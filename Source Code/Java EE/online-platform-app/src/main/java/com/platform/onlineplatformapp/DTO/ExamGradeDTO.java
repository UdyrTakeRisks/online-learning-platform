package com.platform.onlineplatformapp.DTO;

public class ExamGradeDTO {

    // needs exam name, student name, student grade
    private String examName;

    private String studentName;

    private double studentGrade;

    private int center_id;

    public ExamGradeDTO() {

    }

    public ExamGradeDTO(String examName, String studentName, double studentGrade, int center_id) {
        this.examName = examName;
        this.studentName = studentName;
        this.studentGrade = studentGrade;
        this.center_id = center_id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(double studentGrade) {
        this.studentGrade = studentGrade;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }
}
