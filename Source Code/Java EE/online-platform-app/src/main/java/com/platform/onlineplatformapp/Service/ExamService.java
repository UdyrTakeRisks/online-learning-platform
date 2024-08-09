package com.platform.onlineplatformapp.Service;

import com.platform.onlineplatformapp.DAO.ExamDAO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Exam;
import com.platform.onlineplatformapp.Persistence.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamService implements ExamDAO {

    @Override
    public List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exam";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Exam exam = new Exam();
                    exam.setName(resultSet.getString("name"));
                    exam.setDuration(resultSet.getInt("duration"));
                    exam.setDate(resultSet.getString("date"));

                    exams.add(exam);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses: " + e.getMessage());
        }
        return exams;
    }

    @Override
    public String addExam(Exam exam) {
        String sql = "INSERT INTO exam (name, duration, date, grade) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, exam.getName());
            statement.setInt(2, exam.getDuration());
            statement.setString(3, exam.getDate());
            statement.setDouble(4, exam.getGrade());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Creating Exam Failed, no rows affected.";
            }

            return "Exam Created Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Creating Exam: " + e.getMessage();
        }
    }

    @Override
    public int getIdByName(String name) {
        String sql = "SELECT exam_id FROM exam WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("exam_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String addTestCenterExam(int testCenterId, int exam_id) {
        String sql = "INSERT INTO testcenter_exam (center_id, exam_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, testCenterId);
            statement.setInt(2, exam_id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Creating TestCenter_Exam Failed, no rows affected.";
            }

            return "TestCenter_Exam Created Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Creating TestCenter_Exam: " + e.getMessage();
        }
    }

    @Override
    public String addStudentExam(int studentId, int examId) {
        String sql = "INSERT INTO student_exam (student_id, exam_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, examId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Registering Student to Exam Failed, no rows affected.";
            }

            return "Student Registered to Exam Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Registering Student to Exam: " + e.getMessage();
        }
    }

    @Override
    public Map<String, Double> getStudentGradeExams() {
        Map<String, Double> studentGrades = new HashMap<>();
        String sql = "SELECT se.student_grade, e.name AS exam_name " +
                "FROM student_exam se " +
                "JOIN platformDB.exam e ON e.exam_id = se.exam_id ";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String examName = resultSet.getString("exam_name");
                    Double studentGrade = resultSet.getDouble("student_grade");
                    studentGrades.put(examName, studentGrade);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student grades: " + e.getMessage());
        }
        return studentGrades;
    }

    @Override
    public Map<String, Double> getStudentGradeExamsHistory(int student_id) {
        Map<String, Double> studentGrades = new HashMap<>();
        String sql = "SELECT se.student_grade, e.name AS exam_name " +
                "FROM student_exam se " +
                "JOIN platformDB.exam e ON e.exam_id = se.exam_id " +
                "WHERE student_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String examName = resultSet.getString("exam_name");
                    Double studentGrade = resultSet.getDouble("student_grade");
                    studentGrades.put(examName, studentGrade);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student grades: " + e.getMessage());
        }
        return studentGrades;
    }

    @Override
    public String addStudentGrade(int centerId, int studentId, int examId, double studentGrade) {
        String sql = "UPDATE student_exam se " +
                "JOIN testcenter_exam te ON se.exam_id = te.exam_id " +
                "SET se.student_grade = ? " +
                "WHERE se.student_id = ? AND se.exam_id = ? AND te.center_id = ?";

        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, studentGrade);
            statement.setInt(2, studentId);
            statement.setInt(3, examId);
            statement.setInt(4, centerId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                MSG = "Student Grade Added Successfully";
            } else {
                MSG = "Student Grade is not Added";
            }
        } catch (SQLException e) {
            System.err.println("Error Adding Student Grade: " + e.getMessage());
        }
        return MSG;
    }

}
