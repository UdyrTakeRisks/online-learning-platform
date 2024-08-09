package com.platform.onlineplatformapp.Entity;

import jakarta.persistence.Entity;

public class Course {
    private String name;
    private int duration;
    private String category;
    private int capacity;
    private int enrolled_students;
    private int instructor_id;
    private double avgRating;
    private String review_list;
    public Course() {

    }

    public Course(String name, int duration, String category, int capacity, int enrolled_students, int instructor_id, double avgRating, String review_list) {
        this.name = name;
        this.duration = duration;
        this.category = category;
        this.capacity = capacity;
        this.enrolled_students = enrolled_students;
        this.instructor_id = instructor_id;
        this.avgRating = avgRating;
        this.review_list = review_list;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public int getEnrolled_students() {
        return enrolled_students;
    }

    public void setEnrolled_students(int enrolled_students) {
        this.enrolled_students = enrolled_students;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getReview_list() {
        return review_list;
    }

    public void setReview_list(String review_list) {
        this.review_list = review_list;
    }
}
