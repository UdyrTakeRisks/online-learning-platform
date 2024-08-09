package com.platform.onlineplatformapp.Service;

import com.platform.onlineplatformapp.DAO.AdminDAO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;
import com.platform.onlineplatformapp.Persistence.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService implements AdminDAO {

    @Override
    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM instructor";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String affiliation = resultSet.getString("affiliation");
                int years_experience = resultSet.getInt("years_experience");
                String bio = resultSet.getString("bio");
                instructors.add(new Instructor(name, email, affiliation, years_experience, bio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructors;
    }

    @Override
    public Instructor getInstructorById(int instructorId) {
        String sql = "SELECT * FROM instructor WHERE instructor_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    return new Instructor(name, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateInstructor(Instructor instructor) {
        String sql = "UPDATE instructor SET name = ?, password = ? WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, instructor.getName());
            statement.setString(2, instructor.getPassword());
            //statement.setInt(3, instructor.getId()); Ahmed Ali Check this

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The instructor might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteInstructor(String instructorName) {
        String sql = "DELETE FROM instructor WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, instructorName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The instructor might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String affiliation = resultSet.getString("affiliation");
                String bio = resultSet.getString("bio");
                String location = resultSet.getString("location");
                students.add(new Student(name, email, affiliation, bio, location));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM student WHERE student_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    return new Student(name, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, password = ? WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getPassword());
            //statement.setInt(3, student.getId()); Ahmed Ali Check this

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The student might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The student might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Course course = new Course();
                String name = resultSet.getString("name");
                int duration = resultSet.getInt("duration");
                String category = resultSet.getString("category");
                course.setName(name);
                course.setDuration(duration);
                course.setCategory(category);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public Course getCourseById(int id) {
        String sql = "SELECT * FROM course WHERE course_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setInstructor_id(resultSet.getInt("instructor_id"));
                    course.setAvgRating(resultSet.getDouble("avgRating"));
                    course.setReview_list(resultSet.getString("review_list"));
                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE course SET name = ?, duration = ?, category = ?, capacity = ?, enrolled_students = ?, instructor_id = ? " +
                "WHERE course_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getDuration());
            statement.setString(3, course.getCategory());
            statement.setInt(4, course.getCapacity());
            statement.setInt(5, course.getEnrolled_students());
            statement.setInt(6, course.getInstructor_id());
//            statement.setDouble(7, course.getAvgRating());
//            statement.setString(8, course.getReview_list());
//            statement.setInt(9, course.getId()); check this Ahmed Ali

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The course might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCourse(int id) {
        String sql = "DELETE FROM course WHERE course_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No rows affected. The course might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getCoursesByPopularity() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course ORDER BY enrolled_students DESC";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Course course = new Course();
                course.setName(resultSet.getString("name"));
                course.setDuration(resultSet.getInt("duration"));
                course.setCategory(resultSet.getString("category"));
                course.setCapacity(resultSet.getInt("capacity"));
                course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                course.setInstructor_id(resultSet.getInt("instructor_id"));
//                course.setAvgRating(resultSet.getDouble("avgRating"));
//                course.setReview_list(resultSet.getString("review_list"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesByRating() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, AVG(cr.rating) as avgRating FROM course c " +
                "JOIN course_review cr ON c.course_id = cr.course_id " +
                "GROUP BY c.course_id " +
                "ORDER BY avgRating DESC";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Course course = new Course();
                course.setName(resultSet.getString("name"));
                course.setDuration(resultSet.getInt("duration"));
                course.setCategory(resultSet.getString("category"));
                course.setCapacity(resultSet.getInt("capacity"));
                course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                course.setInstructor_id(resultSet.getInt("instructor_id"));
                course.setAvgRating(resultSet.getDouble("avgRating"));
//                course.setReview_list(resultSet.getString("review_list"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
