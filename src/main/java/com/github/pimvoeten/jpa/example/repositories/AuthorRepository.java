package com.github.pimvoeten.jpa.example.repositories;

import com.github.pimvoeten.jpa.example.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
