package com.platform.testservice.Controller;

import com.platform.testservice.DAO.TestCenterDAO;
import com.platform.testservice.DTO.CredentialsDTO;
import com.platform.testservice.DTO.ExamDTO;
import com.platform.testservice.DTO.ExamGradeDTO;
import com.platform.testservice.DTO.TestCenterUpdateDTO;
import com.platform.testservice.Entity.Branch;
import com.platform.testservice.Entity.TestCenter;
import com.platform.testservice.Service.TestCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/testcenter")
public class TestCenterController {

    TestCenterDAO testCenterService;
    RestTemplate restTemplate;

    @Autowired
    public TestCenterController(TestCenterService testCenterService) {
        this.testCenterService = testCenterService;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<String> checkHealth() {
        String response = "Health State on test-center MS is Good";
        return ResponseEntity.ok(response);
    }

    @GetMapping("/retrieve/id") // testing java ee
    public int getTestCenterId(String name) {
        return testCenterService.getIdByName(name);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginTestCenter(@RequestBody TestCenter testCenter) {
        if (testCenter.getName().isEmpty() || testCenter.getPassword().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and Password can't be Empty.");
        TestCenter retrievedTestCenter = testCenterService.getByName(testCenter.getName());
        if (retrievedTestCenter == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Error: test center account is not found");
        if (retrievedTestCenter.getPassword().equals(testCenter.getPassword())) {
            return ResponseEntity.ok("Test Center logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Error: Unauthorized");
    }

    @PutMapping("/update/info") // update email or name or bio
    public ResponseEntity<String> updateTestCenterInfo(@RequestBody TestCenterUpdateDTO testCenterUpdateDTO) {
        int testCenterId = testCenterService.getIdByName(testCenterUpdateDTO.getName());
        String response = testCenterService.editTestCenterInfo(testCenterId, testCenterUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/add/branch/{testCenterName}")
    public ResponseEntity<String> addTestCenterBranchInfo(@RequestBody Branch branch, @PathVariable String testCenterName) {

        int center_id = testCenterService.getIdByName(testCenterName);
        branch.setCenter_id(center_id);
        String response = testCenterService.addTestCenterBranchInfo(branch);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create/exam/{testCenterName}")
    public ResponseEntity<String> createExam(@RequestBody ExamDTO exam, @PathVariable String testCenterName) {
        int testCenterId = testCenterService.getIdByName(testCenterName);
        String URL = "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/exam/create/exam/{testCenterId}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("testCenterId", testCenterId);
        String response = restTemplate.postForObject(URL, exam, String.class, uriVariables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/set/student/grade/{testCenterName}")
    public ResponseEntity<String> setStudentExamGrade(@RequestBody ExamGradeDTO examGradeDTO, @PathVariable String testCenterName) {
        int center_id = testCenterService.getIdByName(testCenterName);
        examGradeDTO.setCenter_id(center_id);
        // talk to java ee
        String URL = "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/exam/set/student/grade";
        restTemplate.put(URL, examGradeDTO); // patch == put ??
        String response = "Student Grade is Added";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view/exams")
    public ResponseEntity<String> viewExams() {
        String URL = "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/exam/view/exams";
        String response = restTemplate.getForObject(URL, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view/student/grades")
    public ResponseEntity<String> viewStudentExamGrades() {
        String URL = "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/exam/view/student/grades";
        String response = restTemplate.getForObject(URL, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add/generated/credentials") // called by java ee only
    public ResponseEntity<String> createGeneratedCredentials(@RequestBody CredentialsDTO credentialsDTO) {
        // add generated name and password from admin to the generated_credentials table
        String response = testCenterService.addGeneratedCredentials(credentialsDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/retrieve/generated/credentials") // 1
    public CredentialsDTO getGeneratedCredentials() {
        return testCenterService.getGeneratedCredential();
    }

    @PostMapping("/add/test/center/credentials") // 2
    public ResponseEntity<String> addTestCenterCredentials() {
        CredentialsDTO credentialsDTO = testCenterService.getGeneratedCredential();
        String response = testCenterService.addTestCenterCredentials(credentialsDTO);
        // remove credentials from generated_credentials table
        String response2 = testCenterService.removeGeneratedCredentials(credentialsDTO);
        return new ResponseEntity<>(response + response2, HttpStatus.OK);
    }

    @GetMapping("/search/location/{locationName}") // called by java ee only
    public Branch searchTestCenterByLocation(@PathVariable String locationName) {
        return testCenterService.getTestCenterByLocation(locationName);
    }

}
