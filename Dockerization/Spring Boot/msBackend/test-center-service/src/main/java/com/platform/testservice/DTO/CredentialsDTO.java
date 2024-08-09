package com.platform.testservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CredentialsDTO {

    private String name;
    private String password;

    public CredentialsDTO() {

    }

    public CredentialsDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
