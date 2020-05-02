package com.github.pimvoeten.jpa.example.controllers.requestmodels;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Value
public class NewBook {
    @NotBlank
    private String title;

    @NotEmpty
    private Set<NewAuthor> authors;
}
