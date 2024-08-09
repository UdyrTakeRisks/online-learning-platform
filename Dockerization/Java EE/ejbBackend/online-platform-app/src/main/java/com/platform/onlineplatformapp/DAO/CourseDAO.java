package com.platform.onlineplatformapp.DAO;

import com.platform.onlineplatformapp.DTO.CourseReviewDTO;
import com.platform.onlineplatformapp.Entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseDAO {
    Course getCourseByName(String courseName);

    List<Course> getCourseByCategory(String courseCategory);

    List<Course> getAllCourses();

    String addCourse(Course course);

    List<Course> sortCoursesByRatings();

    List<Course> getInstructorCourses(int instructor_id);

    List<Course> getAllStudentCourses(int student_id);

    int getIdByName(String name);

    String createCourseReview(int student_id, int course_id, CourseReviewDTO courseReview);

    String addCourseEnrollment(int studentId, int courseId, String studentName, String CourseName, int instructor_id);

    String removeCourseEnrollment(int studentId, int courseId);

    String acceptEnrollment(int studentId, int courseId, int instructor_id);

    String rejectEnrollment(int studentId, int courseId, int instructor_id);

    String addStudentCourse(int studentId, int courseId);

    String updateEnrolledStudents(int course_id);

    Map<String, String> notifyStudentCourseEnrollments(int student_id);

    Map<String, Map<String, String>> notifyInstructorCourseEnrollments(int instructor_id);


}
