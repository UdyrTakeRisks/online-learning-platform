package com.platform.onlineplatformapp.DAO;

import com.platform.onlineplatformapp.Entity.Student;

import java.util.List;

public interface StudentDAO {
    String addStudent(Student student);
    Student getByName(String name);
    int getIdByName(String name);

}
