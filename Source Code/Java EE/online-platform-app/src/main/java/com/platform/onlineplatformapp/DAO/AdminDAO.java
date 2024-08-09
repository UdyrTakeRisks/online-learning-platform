package com.platform.onlineplatformapp.DAO;

import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;

import java.util.List;

public interface AdminDAO {

        List<Instructor> getAllInstructors();
        Instructor getInstructorById(int instructorId);
        void updateInstructor(Instructor instructor);
        void deleteInstructor(String instructorName);
        List<Student> getAllStudents();
        Student getStudentById(int studentId);
        void updateStudent(Student student);
        void deleteStudent(int studentId);
        List<Course> getAllCourses();
        Course getCourseById(int id);
        void updateCourse(Course course);
        void deleteCourse(int id);
        List<Course> getCoursesByPopularity();
        List<Course> getCoursesByRating();



}