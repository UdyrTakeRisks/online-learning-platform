package com.platform.onlineplatformapp.Controller;

import com.platform.onlineplatformapp.DAO.CourseDAO;
import com.platform.onlineplatformapp.DAO.InstructorDAO;
import com.platform.onlineplatformapp.DAO.StudentDAO;
import com.platform.onlineplatformapp.DTO.EnrollCourseDTO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.JsonResponse;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Stateful
@Path("/instructor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstructorController {

    @Context
    HttpServletRequest httpRequest;
    @EJB
    InstructorDAO instructorService;
    @Inject
    CourseDAO courseService;
    @EJB
    StudentDAO studentService;

    @Context
    JsonObject jsonResponse;

    public InstructorController() {

    }

    public InstructorController(InstructorDAO instructorService, CourseDAO courseService, StudentDAO studentService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "Instructor Health state is good";
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse registerInstructor(Instructor instructor) {

        return new JsonResponse(instructorService.addInstructor(instructor));
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginInstructor(Instructor instructor) {
        if (instructor.getName().isEmpty() || instructor.getPassword().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("Name and Password can't be Empty.").build();
        Instructor retrievedInstructor = instructorService.getByName(instructor.getName());
        if (retrievedInstructor == null)
            return Response.status(Response.Status.NOT_FOUND).entity("404 Error: User is not found").build();
        if (retrievedInstructor.getPassword().equals(instructor.getPassword())) {
            // if stateful, we should create a session object here ??
            HttpSession session = httpRequest.getSession(true); // create a new session
            session.setAttribute("instructorSession", instructor);
//            jsonResponse.put("message", "Instructor logged in successfully");
            return Response.ok().entity("Instructor logged in successfully").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("401 Error: Unauthorized").build();
    }

    @Path("/logout")
    @POST
    public Response logout() {
        // Invalidate the session
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate(); // remove user session object
        }
        return Response.ok().entity("Logged out successfully").build();
    }

    @Path("/create/course")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createCourse(Course course) {
        // validate on course like login

        // get instructor credentials from session (stateful) or take it from url (stateless)
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Instructor instructor = (Instructor) session.getAttribute("instructorSession");
        if (instructor == null) {
            return new JsonResponse("Instructor Session is not found/expired. Please log in.");
        }
        int instructor_id = instructorService.getIdByName(instructor.getName());
        course.setInstructor_id(instructor_id);
        return new JsonResponse(courseService.addCourse(course)); // should be reviewed from admin first before publishing
    }

    @Path("/view/courses/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse viewCourseInfo() {
        // view info about each course related to this instructor
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Instructor instructor = (Instructor) session.getAttribute("instructorSession");
        if (instructor == null) {
            return new JsonResponse("Instructor Session is not found/expired. Please log in.");
        }
        int instructor_id = instructorService.getIdByName(instructor.getName());

        return new JsonResponse(courseService.getInstructorCourses(instructor_id));
    }

    // view course enrollments by students for specific instructor to either accept/reject
    // notify endpoint - needs to show student Name : course Name [that needs to enroll in]

    @Path("/notify/course/enrollments")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<String, String>> notifyForCourseEnrollments() {
        // MDB ? or just a query to check for course_enrollment statuses for a specific student
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return Map.of("Message", Map.of("Session not found. please log in first.", ""));
        }
        Instructor instructor = (Instructor) session.getAttribute("instructorSession");
        if (instructor == null) {
            return Map.of("Message", Map.of("Instructor Session is not found/expired. Please log in.", ""));
        }
        int instructorId = instructorService.getIdByName(instructor.getName());

        return courseService.notifyInstructorCourseEnrollments(instructorId);
    }


    @Path("/accept/student/enrollment")
    @PUT // we will update the course_enrollment to turn status -> accepted
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse acceptStudentCourseEnrollment(EnrollCourseDTO enrollCourseDTO) {
        // MDB ?
        // update status of the course enrollment -> accepted
        // should define if the courses is related to the instructor on session
        // should session logic be in a separate class for simplicity
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Instructor instructor = (Instructor) session.getAttribute("instructorSession");
        if (instructor == null) {
            return new JsonResponse("Instructor Session is not found/expired. Please log in.");
        }
        int student_id = studentService.getIdByName(enrollCourseDTO.getStudentName());
        int course_id = courseService.getIdByName(enrollCourseDTO.getCourseName());
        int instructor_id = instructorService.getIdByName(instructor.getName());
        String res1 = courseService.acceptEnrollment(student_id, course_id, instructor_id);
        // add the student in the student_course table
        String res2 = courseService.addStudentCourse(student_id, course_id);
        // update enrolled_students to inc. one
        String res3 = courseService.updateEnrolledStudents(course_id);
        return new JsonResponse(res1 + " \n " + res2 + " \n " + res3);
    }

    @Path("/reject/student/enrollment")
    @PUT // we will update the course_enrollment to turn status -> rejected
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse rejectStudentCourseEnrollment(EnrollCourseDTO enrollCourseDTO) {
        // MDB ?
        // update status of the course enrollment -> rejected
        HttpSession session = httpRequest.getSession(false); // get existing session
        if (session == null) {
            return new JsonResponse("Session not found. please log in first.");
        }
        Instructor instructor = (Instructor) session.getAttribute("instructorSession");
        if (instructor == null) {
            return new JsonResponse("Instructor Session is not found/expired. Please log in.");
        }
        int student_id = studentService.getIdByName(enrollCourseDTO.getStudentName());
        int course_id = courseService.getIdByName(enrollCourseDTO.getCourseName());
        int instructor_id = instructorService.getIdByName(instructor.getName());
        String res = courseService.rejectEnrollment(student_id, course_id, instructor_id);
        return new JsonResponse(res);
    }

}
