package com.platform.testservice.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestCenter {
    private String name;

    private String username;

    private String password;

    private String bio;

    public TestCenter() {

    }

    public TestCenter(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public TestCenter(String name, String username, String password, String bio) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

}
