package com.platform.testservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
