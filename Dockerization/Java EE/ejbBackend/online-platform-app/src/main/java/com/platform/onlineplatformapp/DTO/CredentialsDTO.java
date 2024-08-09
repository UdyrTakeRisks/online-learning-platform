package com.platform.onlineplatformapp.DTO;


public class CredentialsDTO {

    private String name;
    private String password;

    public CredentialsDTO() {

    }

    public CredentialsDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
