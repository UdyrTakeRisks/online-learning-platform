package com.platform.onlineplatformapp.Entity;

public class Instructor {
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private int years_experience;
    private String bio;

    public Instructor(){

    }
    public Instructor(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Instructor(String name, String email, String affiliation, int years_experience, String bio){
        this.name = name;
        this.email = email;
        this.affiliation = affiliation;
        this.years_experience = years_experience;
        this.bio = bio;
    }

    public Instructor(String name, String email, String password, String affiliation, int years_experience, String bio) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.years_experience = years_experience;
        this.bio = bio;
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

    public int getYears_experience() {
        return years_experience;
    }

    public void setYears_experience(int years_experience) {
        this.years_experience = years_experience;
    }
}
