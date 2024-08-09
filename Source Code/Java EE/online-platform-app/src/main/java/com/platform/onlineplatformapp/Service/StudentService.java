package com.platform.onlineplatformapp.Service;

import com.platform.onlineplatformapp.DAO.StudentDAO;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.Persistence.DBConnection;
import jakarta.ejb.Stateful;
import jakarta.enterprise.inject.Default;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateful
public class StudentService implements StudentDAO {

    @Override
    public String addStudent(Student student) {
        String sql = "INSERT INTO student (name, email, password, affiliation, bio, location) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getPassword());
            statement.setString(4, student.getAffiliation());
            statement.setString(5, student.getBio());
            statement.setString(6, student.getLocation());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Register Student Failed, no rows affected.";
            }

            return "Student Registered Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Registering Student: " + e.getMessage();
        }
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        return new Student(name, password);
    }

    private int extractUserIdFromResultSet(ResultSet resultSet) throws SQLException {
        return resultSet.getInt("student_id");
    }

    @Override
    public Student getByName(String name) {
        String sql = "SELECT * FROM student WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractStudentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getIdByName(String name) {
        String sql = "SELECT student_id FROM student WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserIdFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
