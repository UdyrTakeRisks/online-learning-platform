package com.platform.onlineplatformapp.Controller;

import com.platform.onlineplatformapp.DAO.AdminDAO;
import com.platform.onlineplatformapp.DAO.CourseDAO;
import com.platform.onlineplatformapp.DAO.InstructorDAO;
import com.platform.onlineplatformapp.DAO.StudentDAO;
import com.platform.onlineplatformapp.DTO.CredentialsDTO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.JsonResponse;
import com.platform.onlineplatformapp.Service.PasswordGenerator;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminController {
    AdminDAO adminService;
    CourseDAO courseService;
    InstructorDAO instructorService;
    StudentDAO studentService;

    public AdminController() {

    }

    @Inject
    public AdminController(CourseDAO courseService, InstructorDAO instructorService, StudentDAO studentService,AdminDAO adminService){
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.adminService = adminService;
    }

    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "Admin Health state is good";
    }

    @Path("/view/instructors") // ok
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Instructor> viewAllInstructors() {
        return adminService.getAllInstructors();
    }

    @Path("/instructor/{id}") //
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Instructor getInstructorById(@PathParam("id") int instructorId) {
        return adminService.getInstructorById(instructorId);
    }

    @Path("update/instructor") //
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateInstructor(Instructor instructor) {
        adminService.updateInstructor(instructor);
        return new JsonResponse("Instructor updated successfully");
    }

    @Path("delete/instructor/{instructorName}") //
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse deleteInstructor(@PathParam("instructorName") String instructorName) {
        adminService.deleteInstructor(instructorName);
        return new JsonResponse("Instructor deleted successfully");
    }

    @Path("/view/students") //ok
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> viewAllStudents() {
        return adminService.getAllStudents();
    }


    @Path("/student/{id}") //
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentById(@PathParam("id") int studentId) {
        return adminService.getStudentById(studentId);
    }

    @Path("update/student") //
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateStudent(Student student) {
        adminService.updateStudent(student);
        return new JsonResponse("Student updated successfully");
    }

    @Path("delete/student/{id}") //
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse deleteStudent(@PathParam("id") int studentId) {
        adminService.deleteStudent(studentId);
        return new JsonResponse("Student deleted successfully");
    }

    // manage user accounts

    @Path("/review/course/content") // ok
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> reviewCourseContent() {
        return adminService.getAllCourses();
    }


    @Path("/course/{id}") //
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourseById(@PathParam("id") int id) {
        return adminService.getCourseById(id);
    }


    @Path("/edit/course") //
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse editCourse(Course course) {
        adminService.updateCourse(course);
        return new JsonResponse("Course updated successfully");
    }

    @Path("/remove/course/{id}") //
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse removeCourse(@PathParam("id") int id) {
        adminService.deleteCourse(id);
        return new JsonResponse("Course removed successfully");
    }

    @Path("/track/platform/usage") //
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse trackPlatformUsage(Course course) {

        return new JsonResponse("");
    }

    @Path("/check/course/popularity") // ??
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> checkCoursePopularity() {
        return adminService.getCoursesByPopularity();
    }

    @Path("/check/course/reviews")
    @GET
    @Produces(MediaType.APPLICATION_JSON) // ??
    public List<Course> checkCourseReviews() {
        return adminService.getCoursesByRating();
    }

    // etc

    @Path("/create/test-center/account")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createTestCenterAccount() throws IOException { // talk to spring to create the test-center account
        List<String> uniqueNames = new ArrayList<>(5);
        uniqueNames.addAll(List.of("iti", "creativa", "itida", "redhat", "udemy"));
        Random random = new Random();
        String name = uniqueNames.get(random.nextInt(uniqueNames.size()));
        uniqueNames.remove(name);
        String password = PasswordGenerator.generateRandomPassword(6);
        CredentialsDTO credentialsDTO = new CredentialsDTO(name, password);
        // Convert object to JSON
        Jsonb jsonb = JsonbBuilder.create();
        String jsonRequest = jsonb.toJson(credentialsDTO);

        URL url = new URL("http://localhost:9191/api/testcenter/add/generated/credentials");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST"); // Change request method to POST
        conn.setRequestProperty("Content-Type", "application/json"); // Set content type to JSON
        conn.setDoOutput(true);

        // Write the JSON request to the connection
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes();
            os.write(input, 0, input.length);
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JsonResponse(response.toString());
        }
        return new JsonResponse("Error Occurred Creating test center account.");
    }

}
