package com.github.pimvoeten.jpa.example.repositories;

import com.github.pimvoeten.jpa.example.entities.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID>, BaseRepository<Author, UUID> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    @EntityGraph("author-with-books")
    Author getById(UUID id);
}
