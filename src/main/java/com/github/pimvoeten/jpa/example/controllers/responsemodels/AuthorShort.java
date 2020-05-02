package com.github.pimvoeten.jpa.example.controllers.responsemodels;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthorShort {
    private UUID id;
    private String firstName;
    private String lastName;
}
