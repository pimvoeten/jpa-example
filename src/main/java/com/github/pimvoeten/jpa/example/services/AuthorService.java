package com.github.pimvoeten.jpa.example.services;

import com.github.pimvoeten.jpa.example.controllers.mappers.AuthorMapper;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorShort;
import com.github.pimvoeten.jpa.example.repositories.AuthorRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    @Resource
    AuthorRepository authorRepository;

    @Resource
    AuthorMapper authorMapper;

    public Page<AuthorShort> getAuthorList(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(person -> authorMapper.fromEntityToAuthor(person));
    }

//    @Cacheable("authors")
    public AuthorDetails getAuthor(UUID id) {
        return authorRepository.findById(id)
                .map(person -> authorMapper.fromEntityToAuthorDetails(person))
                .orElse(null);
    }

    public AuthorDetails getAuthorWithGraph(UUID id) {
        return authorMapper.fromEntityToAuthorDetails(
                authorRepository.findWithGraph(id, "author-with-books")
        );
    }

    public AuthorDetails getAuthorWithAnnotatedGraph(UUID id) {
        return authorMapper.fromEntityToAuthorDetails(
                authorRepository.getById(id)
        );
    }

    @Transactional
    public void deleteAuthor(UUID id) {
        authorRepository.deleteById(id);
    }
}
