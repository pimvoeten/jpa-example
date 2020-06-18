package com.github.pimvoeten.jpa.example.controllers;

import com.github.pimvoeten.jpa.example.controllers.requestmodels.KnownBook;
import com.github.pimvoeten.jpa.example.controllers.requestmodels.NewBook;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.BookDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.Title;
import com.github.pimvoeten.jpa.example.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping
    public Page<Title> getListOfTitles(
            @PageableDefault(
                    page = 0,
                    size = 50,
                    sort = {"title"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return bookService.getListOfTitles(pageable);
    }

    @GetMapping(path = "/count")
    public Long countBooks() {
        return bookService.countAllBooks();
    }

    @GetMapping(path = "/{id}")
    public BookDetails getBookDetails(@PathVariable UUID id) {
        return bookService.getBook(id);
    }

    @GetMapping(path = "/{id}/2")
    public BookDetails getBookDetailsJoin(@PathVariable UUID id) {
        return bookService.getBookWithAuthors(id);
    }

    @GetMapping(path = "/{id}/3")
    public BookDetails getBookDetailsJoinNamed(@PathVariable UUID id) {
        return bookService.getBookWithAuthorsNamed(id);
    }

    @PostMapping
    public ResponseEntity<Title> createBook(@RequestBody @Valid NewBook newBook) {
        return bookService
                .createNewBook(newBook)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody @Valid KnownBook book, @PathVariable UUID id) {
        bookService.updateBook(id, book);
    }
}
