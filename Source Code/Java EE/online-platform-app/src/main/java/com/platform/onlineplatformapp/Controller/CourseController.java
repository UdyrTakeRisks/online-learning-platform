package com.platform.onlineplatformapp.Controller;

import com.platform.onlineplatformapp.DAO.CourseDAO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.JsonResponse;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Stateless
@Path("/course")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseController {

    CourseDAO courseService;

    public CourseController() {

    }

    @Inject
    public CourseController(CourseDAO courseService) {
        this.courseService = courseService;
    }

    @Path("/view/all/courses")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> viewAllCourses() {
        return courseService.getAllCourses();
    }

    @Path("/search/course/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Course searchCourseByName(@PathParam("name") String courseName) {
        String crsName = courseName.toLowerCase();
        return courseService.getCourseByName(crsName);
    }

    @Path("/search/courses/{category}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> searchCourseByCategory(@PathParam("category") String courseCategory) {
        String crsCategory = courseCategory.toLowerCase();
        return courseService.getCourseByCategory(crsCategory);
    }

    @Path("/sort/courses")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> sortCoursesByRatings() {
        // view courses based on rates desc ? - use review table
        return courseService.sortCoursesByRatings();
    }
}
