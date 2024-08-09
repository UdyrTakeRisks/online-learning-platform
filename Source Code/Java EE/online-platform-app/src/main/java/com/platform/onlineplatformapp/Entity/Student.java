package com.platform.onlineplatformapp.Entity;

public class Student {
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private String bio;

    private String location;

    public Student() {

    }

    public Student(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Student(String name, String email, String affiliation, String bio, String location) {
        this.name = name;
        this.email = email;
        this.affiliation = affiliation;
        this.bio = bio;
        this.location = location;
    }

    public Student(String name, String email, String password, String affiliation, String bio, String location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.bio = bio;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
