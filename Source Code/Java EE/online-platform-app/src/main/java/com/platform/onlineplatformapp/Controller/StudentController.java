package com.platform.onlineplatformapp.Controller;

import com.platform.onlineplatformapp.DAO.ExamDAO;
import com.platform.onlineplatformapp.DAO.InstructorDAO;
import com.platform.onlineplatformapp.DTO.CourseReviewDTO;
import com.platform.onlineplatformapp.DAO.CourseDAO;
import com.platform.onlineplatformapp.DAO.StudentDAO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.JsonResponse;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;


@Stateful
@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

    @Context
    HttpServletRequest httpRequest;
    StudentDAO studentService;
    CourseDAO courseService;
    ExamDAO examService;
    InstructorDAO instructorService;

    public StudentController() {

    }

    // inject service interface class to interact with the database
    @Inject
    public StudentController(StudentDAO studentService, CourseDAO courseService, ExamDAO examService, InstructorDAO instructorService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.examService = examService;
        this.instructorService = instructorService;
    }


    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "Student Health state is good";
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse registerStudent(Student student) {

        return new JsonResponse(studentService.addStudent(student));
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse loginStudent(Student student) {
        if (student.getName().isEmpty() || student.getPassword().isEmpty())
            return new JsonResponse("Name and Password can't be Empty.");
        Student retrievedStudent = studentService.getByName(student.getName());
        if (retrievedStudent == null)
            return new JsonResponse("404 Error: User is not found");
        if (retrievedStudent.getPassword().equals(student.getPassword())) {
            // if stateful, we should create a session object here ??
            // if stateful, we should create a session object here ??
            HttpSession session = httpRequest.getSession(true); // create a new session
            session.setAttribute("studentSession", student);
//            jsonResponse.put("message", "Instructor logged in successfully");
            return new JsonResponse("Student logged in successfully");
        }
        return new JsonResponse("401 Error: Unauthorized");
    }

    @Path("/logout")
    @POST
    public Response logout() {
        // Invalidate the session
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session != null) {
            session.invalidate(); // remove user session object
        }
        return Response.ok().entity("Logged out successfully").build();
    }

    @Path("/view/course/enrollments")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse viewCourseEnrollments() {
        // view course enrollments for each course related to this student
        // use join table of student_course (stackoverflow example)
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return new JsonResponse("Student Session is not found/expired. Please log in.");
        }
        int student_id = studentService.getIdByName(student.getName());
        return new JsonResponse(courseService.getAllStudentCourses(student_id));
    }

    @Path("/make/course/enrollment/{courseName}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse makeCourseEnrollment(@PathParam("courseName") String courseName) {
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return new JsonResponse("Student Session is not found/expired. Please log in.");
        }
        int studentId = studentService.getIdByName(student.getName());
        int courseId = courseService.getIdByName(courseName);
        int instructor_id = instructorService.getIdByCourseName(courseName);
        // validate if course is found or not
        if (courseId == 0)
            return new JsonResponse("Course is not found to enroll.");
        return new JsonResponse(courseService.addCourseEnrollment(studentId, courseId, student.getName(), courseName, instructor_id));
    }

    @Path("/cancel/course/enrollment/{courseName}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse cancelCourseEnrollment(@PathParam("courseName") String courseName) {
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return new JsonResponse("Student Session is not found/expired. Please log in.");
        }
        int studentId = studentService.getIdByName(student.getName());
        int courseId = courseService.getIdByName(courseName);
        // validate if course is found or not
        if (courseId == 0)
            return new JsonResponse("Course is not found to cancel enroll.");
        return new JsonResponse(courseService.removeCourseEnrollment(studentId, courseId));
    }

    @Path("/notify/course/enrollments")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> notifyForCourseEnrollments() {
        // MDB ? or just a query to check for course_enrollment statuses for a specific student
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return Map.of("Message", "Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return Map.of("Message", "Student Session is not found/expired. Please log in.");
        }
        int studentId = studentService.getIdByName(student.getName());

        return courseService.notifyStudentCourseEnrollments(studentId);
    }

    @Path("/make/course/review")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse makeCourseReview(CourseReviewDTO courseReview) {
        // enter course name, rating, and review description
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return new JsonResponse("Student Session is not found/expired. Please log in.");
        }
        int student_id = studentService.getIdByName(student.getName());
        int course_id = courseService.getIdByName(courseReview.getName());
        return new JsonResponse(courseService.createCourseReview(student_id, course_id, courseReview));
    }

    @Path("/search/available/test-centers/{location}") // called here (issue ##) test it with string without spaces
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchAvailableTestCenters(@PathParam("location") String testCenterLocation) throws IOException {
        // call a test-center-service endpoint that search for test centers based on its location
        String urlString = String.format("http://localhost:9191/api/search/location/%s", testCenterLocation);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return "No Test Center Branches Found Nearby";
    }

    @Path("/register/exam/{examName}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse registerExam(@PathParam("examName") String examName) {
        // needs student_id and exam_id
        // addStudentExam in the student_exam table
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return new JsonResponse("Student Session is not found/expired. Please log in.");
        }
        int student_id = studentService.getIdByName(student.getName());
        int exam_id = examService.getIdByName(examName);
        // validate if examId found or not
        if (exam_id == 0)
            return new JsonResponse("Exam is not found");
        return new JsonResponse(examService.addStudentExam(student_id, exam_id));
    }

    @Path("/view/exam/grades/history")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> viewExamGradesHistory() {
        // needs student_id and exam_id
        // view the students' grade by using student_exam table in getting his all student_grades for all exams
        // join with exam table to get the exam names
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return Map.of("Session not found. please log in first.", 0.0);
        }
        Student student = (Student) session.getAttribute("studentSession");
        if (student == null) {
            return Map.of("Student Session is not found/expired. Please log in.", 0.0);
        }
        int student_id = studentService.getIdByName(student.getName());

        return examService.getStudentGradeExamsHistory(student_id);
    }

}
