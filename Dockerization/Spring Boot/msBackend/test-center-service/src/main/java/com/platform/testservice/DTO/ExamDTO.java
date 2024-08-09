package com.platform.testservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ExamDTO {

    private String name;
    private int duration;
    private String date;
    private double grade;

    public ExamDTO() {
        
    }

    public ExamDTO(String name, int duration, String date, double grade) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.grade = grade;
    }

}
