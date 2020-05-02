package com.github.pimvoeten.jpa.example.controllers.requestmodels;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NewAuthor {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;
}
