package com.platform.onlineplatformapp.Entity;

import jakarta.json.bind.annotation.JsonbDateFormat;

import java.util.Date;

public class Exam {

    private String name;
    private int duration;

    private String date;

    private double grade;

    public Exam() {

    }

    public Exam(String name, int duration, String date, double grade) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
