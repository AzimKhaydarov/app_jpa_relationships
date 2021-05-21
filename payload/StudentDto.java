package com.example.app_jpa_relationships.payload;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto {

    private String firstName;

    private String lastName;

    private String city;
    private String district;
    private String street;

    private Integer groupId;

    private List<Integer> subjects;
}
