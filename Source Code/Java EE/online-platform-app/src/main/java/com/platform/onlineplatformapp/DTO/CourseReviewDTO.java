package com.platform.onlineplatformapp.DTO;


public class CourseReviewDTO {
    private String name;
    private double rating;
    private String review_list;

    public CourseReviewDTO() {

    }

    public CourseReviewDTO(String name, double rating, String review_list) {
        this.name = name;
        this.rating = rating;
        this.review_list = review_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public String getReview_list() {
        return review_list;
    }

    public void setReview_list(String review_list) {
        this.review_list = review_list;
    }
}
