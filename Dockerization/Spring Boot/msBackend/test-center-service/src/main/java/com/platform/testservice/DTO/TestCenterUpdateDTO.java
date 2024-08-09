package com.platform.testservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCenterUpdateDTO {
    private String name;
    private String email;
    private String bio;

    public TestCenterUpdateDTO() {

    }

    public TestCenterUpdateDTO(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = bio;
    }
}
