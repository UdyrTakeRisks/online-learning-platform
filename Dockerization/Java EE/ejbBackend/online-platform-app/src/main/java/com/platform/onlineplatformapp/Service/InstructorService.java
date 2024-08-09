package com.platform.onlineplatformapp.Service;

import com.platform.onlineplatformapp.DAO.InstructorDAO;
import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.Persistence.DBConnection;
import jakarta.ejb.Stateful;
import jakarta.enterprise.inject.Default;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateful
public class InstructorService implements InstructorDAO {

    @Override
    public String addInstructor(Instructor instructor) {
        String sql = "INSERT INTO instructor (name, email, password, affiliation, years_experience, bio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, instructor.getName());
            statement.setString(2, instructor.getEmail());
            statement.setString(3, instructor.getPassword());
            statement.setString(4, instructor.getAffiliation());
            statement.setInt(5, instructor.getYears_experience());
            statement.setString(6, instructor.getBio());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Register Instructor Failed, no rows affected.";
            }

            return "Instructor Registered Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Registering Instructor: " + e.getMessage();
        }
    }

    private Instructor extractInstructorFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        return new Instructor(name, password);
    }

    private int extractUserIdFromResultSet(ResultSet resultSet) throws SQLException {
        return resultSet.getInt("instructor_id");
    }

    @Override
    public Instructor getByName(String name) {
        String sql = "SELECT * FROM instructor WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractInstructorFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getIdByName(String name) {
        String sql = "SELECT instructor_id FROM instructor WHERE name = ?";

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

    @Override
    public int getIdByCourseName(String courseName) {
        String sql = "SELECT instructor_id FROM course WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
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
