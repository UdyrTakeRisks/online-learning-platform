package com.platform.onlineplatformapp;

import com.platform.onlineplatformapp.Entity.Course;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class JsonResponse {
    private String Json;
    private List<Course> courses;

    public JsonResponse() {

    }

    public JsonResponse(List<Course> courses) {
        this.courses = courses;
    }

    public JsonResponse(String Json) {
        this.Json = Json;
    }

    public String getJsonResponse() {
        return Json;
    }

    public void setJsonResponse(String health) {
        this.Json = Json;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
