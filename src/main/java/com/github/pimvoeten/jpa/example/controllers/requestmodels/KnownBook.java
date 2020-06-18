package com.github.pimvoeten.jpa.example.controllers.requestmodels;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
public class KnownBook {

    @NotBlank
    private String title;

    @NotEmpty
    private List<NewAuthor> authors;
}
