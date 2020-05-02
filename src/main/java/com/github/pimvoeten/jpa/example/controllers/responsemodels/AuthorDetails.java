package com.github.pimvoeten.jpa.example.controllers.responsemodels;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AuthorDetails {
    private UUID id;
    private String firstName;
    private String lastName;
    private List<Title> books;
}
