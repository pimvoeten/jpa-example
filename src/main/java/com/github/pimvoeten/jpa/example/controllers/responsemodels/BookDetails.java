package com.github.pimvoeten.jpa.example.controllers.responsemodels;

import lombok.Data;

import java.util.List;

@Data
public class BookDetails {

    private String id;
    private String title;
    private String isbn;
    private List<AuthorShort> authors;
}
