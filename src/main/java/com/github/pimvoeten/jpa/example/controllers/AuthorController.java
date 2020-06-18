package com.github.pimvoeten.jpa.example.controllers;

import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorShort;
import com.github.pimvoeten.jpa.example.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "authors")
public class AuthorController {

    @Resource
    AuthorService authorService;

    @GetMapping
    public Page<AuthorShort> getAuthorList(Pageable pageable) {
        return authorService.getAuthorList(pageable);
    }

    @GetMapping(path = "{id}")
    public AuthorDetails getAuthor(@PathVariable UUID id) {
        return authorService.getAuthor(id);
    }

   @GetMapping(path = "{id}/2")
    public AuthorDetails getAuthorWithGraph(@PathVariable UUID id) {
        return authorService.getAuthorWithGraph(id);
    }

   @GetMapping(path = "{id}/3")
    public AuthorDetails getAuthorWithAnnotatedGraph(@PathVariable UUID id) {
        return authorService.getAuthorWithAnnotatedGraph(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
    }

}
