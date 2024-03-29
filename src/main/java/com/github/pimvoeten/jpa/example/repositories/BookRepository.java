package com.github.pimvoeten.jpa.example.repositories;

import com.github.pimvoeten.jpa.example.entities.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, BaseRepository<Book, UUID> {

    boolean existsByTitle(String title);

    Book findByTitle(String title);

    @EntityGraph("book-with-authors")
    Book getById(UUID id);
}
