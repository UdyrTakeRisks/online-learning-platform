package com.platform.onlineplatformapp.DAO;

import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Exam;

import java.util.List;
import java.util.Map;

public interface ExamDAO {
    List<Exam> getAllExams();

    String addExam(Exam exam);

    int getIdByName(String name);

    String addTestCenterExam(int testCenterId, int exam_id);

    String addStudentExam(int studentId, int examId);

    Map<String, Double> getStudentGradeExams();

    Map<String, Double> getStudentGradeExamsHistory(int student_id);

    String addStudentGrade(int centerId, int studentId, int examId, double studentGrade);
}

