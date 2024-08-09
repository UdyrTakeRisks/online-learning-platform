package com.platform.onlineplatformapp.Controller;

import com.platform.onlineplatformapp.DAO.ExamDAO;
import com.platform.onlineplatformapp.DAO.StudentDAO;
import com.platform.onlineplatformapp.DTO.ExamGradeDTO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Exam;
import com.platform.onlineplatformapp.JsonResponse;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Stateless
@Path("/exam")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamController { // these endpoints will be invoked using the testcenter-service in spring boot

    ExamDAO examService;
    StudentDAO studentService;

    public ExamController() {

    }

    @Inject
    public ExamController(ExamDAO examService, StudentDAO studentService) {
        this.examService = examService;
        this.studentService = studentService;
    }

    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "Exam Health state is good";
    }

    @Path("/get/testcenter/id") // for spring only [testing wise]
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTestCenterId() throws IOException {
        // call getTestCenterId endpoint from testcenter-service
        URL url = new URL("http://localhost:9191/api/testcenter/retrieve/id");
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
        return "Id is not found";
    }


    @Path("/create/exam/{testCenterId}") // for spring only
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createExam(Exam exam, @PathParam("testCenterId") int testCenterId) throws IOException {
        // called by createExam endpoint in testcenter-service
        String result = examService.addExam(exam);
        if (result.equals("Exam Created Successfully.")) {
//            String testCenterIdStr = getTestCenterId(testCenterName);
//            int testCenterId = Integer.parseInt(testCenterIdStr);
            int exam_id = examService.getIdByName(exam.getName());
            String res = examService.addTestCenterExam(testCenterId, exam_id);
            return new JsonResponse(result + "\n" + res);
        }
        return new JsonResponse("Creating Exam Failed");
    }

    @Path("/set/student/grade") // for spring only
    @PUT // student should be already registered for exam , testcenter should update student grade to it
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse setStudentExamGrade(ExamGradeDTO examGradeDTO) {
        // called by setExamGrade endpoint in testcenter-service
        // needs exam name, student name, student grade
        int student_id = studentService.getIdByName(examGradeDTO.getStudentName());
        int exam_id = examService.getIdByName(examGradeDTO.getExamName());
        int center_id = examGradeDTO.getCenter_id();
        // validate ids if found or not
        if(student_id == 0 && exam_id == 0)
            return new JsonResponse("Student or Exam is not found");
        return new JsonResponse(examService.addStudentGrade(center_id, student_id, exam_id, examGradeDTO.getStudentGrade()));
    }

    @Path("/view/exams") // for spring only
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Exam> viewAllExams() {
        // called by getAllExams endpoint from testcenter-service
        // call a get query to get all exams
        return examService.getAllExams();
    }

    @Path("/view/student/grades") // for spring only
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> viewStudentExamGrades() {
        // called by getStudentGrades endpoint from testcenter-service
        return examService.getStudentGradeExams();
    }

}
