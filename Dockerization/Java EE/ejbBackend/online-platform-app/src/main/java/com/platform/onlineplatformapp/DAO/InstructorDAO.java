package com.platform.onlineplatformapp.DAO;

import com.platform.onlineplatformapp.Entity.Instructor;
import com.platform.onlineplatformapp.Entity.Student;

import java.util.List;

public interface InstructorDAO {
    String addInstructor(Instructor instructor);
    Instructor getByName(String name);
    int getIdByName(String name);

    int getIdByCourseName(String courseName);
}
