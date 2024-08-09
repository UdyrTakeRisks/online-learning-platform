package com.platform.testservice.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Branch {

    private int center_id;

    private String name;

    private String address;

    private String location;

    private String center_name;

    private String center_bio;

    public Branch() {

    }

    public Branch(String name, String address, String location, String center_name, String center_bio) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.center_name = center_name;
        this.center_bio = center_bio;
    }
}
