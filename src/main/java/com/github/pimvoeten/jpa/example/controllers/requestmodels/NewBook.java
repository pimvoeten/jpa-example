package com.github.pimvoeten.jpa.example.controllers.requestmodels;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
public class NewBook {
    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(min = 13, max = 13)
    private String isbn;

    @NotEmpty
    @Size(min = 1)
    @Valid
    private Set<NewAuthor> authors;
}
