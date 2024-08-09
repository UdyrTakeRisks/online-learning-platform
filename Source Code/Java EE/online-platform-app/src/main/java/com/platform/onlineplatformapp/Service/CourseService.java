package com.platform.onlineplatformapp.Service;

import com.platform.onlineplatformapp.DTO.CourseReviewDTO;
import com.platform.onlineplatformapp.DAO.CourseDAO;
import com.platform.onlineplatformapp.Entity.Course;
import com.platform.onlineplatformapp.Persistence.DBConnection;
import jakarta.enterprise.inject.Default;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Default
public class CourseService implements CourseDAO {

    @Override
    public String addCourse(Course course) {
        String sql = "INSERT INTO course (name, duration, category, capacity, enrolled_students, instructor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getDuration());
            statement.setString(3, course.getCategory());
            statement.setInt(4, course.getCapacity());
            statement.setInt(5, course.getEnrolled_students());
            statement.setInt(6, course.getInstructor_id());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Creating Course Failed, no rows affected.";
            }

            return "Course Created Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Creating Course: " + e.getMessage();
        }
    }

    @Override
    public String addCourseEnrollment(int studentId, int courseId, String studentName, String CourseName, int instructor_id) {
        String sql = "INSERT INTO course_enrollment (student_id, course_id, student_name, course_name, instructor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setString(3, studentName);
            statement.setString(4, CourseName);
            statement.setInt(5, instructor_id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Creating Course Enrollment Failed, no rows affected.";
            }

            return "Course Enrollment Created Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Creating Course Enrollment: " + e.getMessage();
        }
    }

    @Override
    public String removeCourseEnrollment(int studentId, int courseId) {
        String sql = "DELETE FROM course_enrollment WHERE student_id = ? AND course_id = ?";
        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                MSG = "Course Enrollment Removed Successfully";
            } else {
                MSG = "Course Enrollment is not found";
            }

        } catch (SQLException e) {
            MSG = "Error Removing Course Enrollment: " + e.getMessage();
        }
        return MSG;
    }

    @Override
    public String acceptEnrollment(int studentId, int courseId, int instructor_id) {
        String sql = "UPDATE course_enrollment " +
                "SET status = 'accepted' " +
                "WHERE student_id = ? AND course_id = ? AND instructor_id = ?";

        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setInt(3, instructor_id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                MSG = "Course Enrollment Accepted Successfully";
            } else {
                MSG = "Course Enrollment is not Accepted";
            }
        } catch (SQLException e) {
            MSG = "Error Accepting Course Enrollment: " + e.getMessage();
        }
        return MSG;
    }

    @Override
    public String rejectEnrollment(int studentId, int courseId, int instructor_id) {
        String sql = "UPDATE course_enrollment " +
                "SET status = 'rejected' " +
                "WHERE student_id = ? AND course_id = ? AND instructor_id = ?";

        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setInt(3, instructor_id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                MSG = "Course Enrollment Rejected Successfully";
            } else {
                MSG = "Course Enrollment is not Rejected";
            }
        } catch (SQLException e) {
            System.err.println("Error Rejecting Course Enrollment: " + e.getMessage());
        }
        return MSG;
    }

    @Override
    public String addStudentCourse(int studentId, int courseId) {
        String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Can't Add Student to the Course, no rows affected.";
            }

            return "Added Student to the Course Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Adding Student to the Course: " + e.getMessage();
        }
    }

    @Override
    public String updateEnrolledStudents(int course_id) {
        String sql = "UPDATE course " +
                "SET enrolled_students = enrolled_students + 1 " +
                "WHERE course_id = ?";

        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, course_id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                MSG = "Enrollment Students Updated Successfully";
            } else {
                MSG = "Enrollment Students is not Updated";
            }
        } catch (SQLException e) {
            MSG = "Error Updating Enrollment Students: " + e.getMessage();
        }
        return MSG;
    }

    @Override
    public Map<String, String> notifyStudentCourseEnrollments(int student_id) {
        Map<String, String> courseStatuses = new HashMap<>();
        String sql = "SELECT ce.status, c.name AS course_name " +
                "FROM course_enrollment ce " +
                "JOIN course c ON ce.course_id = c.course_id " +
                "WHERE ce.student_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    String statusValue = resultSet.getString("status");
                    courseStatuses.put(courseName, statusValue);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting instructor courses: " + e.getMessage());
        }
        return courseStatuses;
    }

    @Override
    public Map<String, Map<String, String>> notifyInstructorCourseEnrollments(int instructor_id) {
        Map<String, Map<String, String>> courseStatuses = new HashMap<>();
        String sql = "SELECT ce.status, ce.student_name, c.name AS course_name " +
                "FROM course_enrollment ce " +
                "JOIN course c ON ce.course_id = c.course_id " +
                "WHERE ce.instructor_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructor_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String studentName = resultSet.getString("student_name");
                    String courseName = resultSet.getString("course_name");
                    String statusValue = resultSet.getString("status");
                    courseStatuses.put(studentName, Map.of(courseName, statusValue));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting instructor courses: " + e.getMessage());
        }
        return courseStatuses;
    }

    @Override
    public Course getCourseByName(String courseName) {
        Course course = new Course();
        String sql = "Select * from course where name = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) { // resultSet.next function used to iterate for each row in database
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setInstructor_id(resultSet.getInt("instructor_id"));
                }
                if (course.getName() == null)
                    return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting course by name: " + e.getMessage());
        }
        return course;
    }

    @Override
    public List<Course> getCourseByCategory(String courseCategory) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE category = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseCategory);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setInstructor_id(resultSet.getInt("instructor_id"));
                    courses.add(course);
                    if (course.getCategory() == null)
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses by category: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setInstructor_id(resultSet.getInt("instructor_id"));
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public List<Course> sortCoursesByRatings() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT *, AVG(r.rating) AS average_rating, r.review_list " +
                "FROM course c " +
                "LEFT JOIN course_review r ON c.course_id = r.course_id " +
                "GROUP BY c.course_id " +
                "ORDER BY average_rating DESC";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setAvgRating(resultSet.getDouble("average_rating"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setReview_list(resultSet.getString("review_list"));
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses ratings: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public List<Course> getInstructorCourses(int instructor_id) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE instructor_id = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructor_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    course.setInstructor_id(resultSet.getInt("instructor_id"));
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting instructor courses: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public List<Course> getAllStudentCourses(int student_id) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course c " +
                "JOIN student_course sc ON c.course_id = sc.course_id " +
                "WHERE student_id = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCategory(resultSet.getString("category"));
                    course.setCapacity(resultSet.getInt("capacity"));
                    course.setEnrolled_students(resultSet.getInt("enrolled_students"));
                    courses.add(course);
                    if (course.getName() == null)
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting instructor courses: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public int getIdByName(String name) {
        String sql = "SELECT course_id FROM course WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("course_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String createCourseReview(int student_id, int course_id, CourseReviewDTO courseReview) {
        String sql = "INSERT INTO course_review (student_id, course_id, rating, review_list) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student_id);
            statement.setInt(2, course_id);
            statement.setDouble(3, courseReview.getRating());
            statement.setString(4, courseReview.getReview_list());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Can't Add Course Review, no rows affected.";
            }
            return "Added Course Review Successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Adding Course Review: " + e.getMessage();
        }
    }


}
