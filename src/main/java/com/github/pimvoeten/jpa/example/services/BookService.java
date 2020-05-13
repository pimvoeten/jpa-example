package com.github.pimvoeten.jpa.example.services;

import com.github.pimvoeten.jpa.example.controllers.mappers.AuthorMapper;
import com.github.pimvoeten.jpa.example.controllers.mappers.BookMapper;
import com.github.pimvoeten.jpa.example.controllers.requestmodels.KnownBook;
import com.github.pimvoeten.jpa.example.controllers.requestmodels.NewAuthor;
import com.github.pimvoeten.jpa.example.controllers.requestmodels.NewBook;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.BookDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.Title;
import com.github.pimvoeten.jpa.example.entities.Author;
import com.github.pimvoeten.jpa.example.entities.Book;
import com.github.pimvoeten.jpa.example.repositories.AuthorRepository;
import com.github.pimvoeten.jpa.example.repositories.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Resource
    BookRepository bookRepository;

    @Resource
    AuthorRepository authorRepository;

    @Resource
    AuthorMapper authorMapper;

    @Resource
    BookMapper bookMapper;

    /**
     * Creates a new book if it doesn't exist yet
     * Creates a new person if he/she is not known yet
     *
     * @param newBook
     */
    @Transactional
    public Optional<Title> createNewBook(NewBook newBook) {
        if (bookRepository.existsByTitle(newBook.getTitle())) {
            throw new IllegalArgumentException(String.format("Book with title %s already exists", newBook.getTitle()));
        }

        // Create new Authors if necessary
        handleAuthorList(newBook.getAuthors());

        // Get the Author IDs
        final Set<Author> authors = newBook.getAuthors().stream()
                .map(author -> authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()))
                .collect(Collectors.toSet());

        final Book book = bookMapper.toEntity(newBook);
        book.setAuthors(authors);
        bookRepository.save(book);

        return Optional.of(bookMapper.fromEntityToTitle(book));
    }

    /**
     * Updates a new book
     * Creates a new person if he/she is not known yet
     *
     * @param id
     * @param knownBook
     */
    @Transactional
    public void updateBook(UUID id, KnownBook knownBook) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Book with id %s does not exist", id));
        }

        // Create new Authors if necessary
        handleAuthorList(knownBook.getAuthors());

        // Get the Author IDs
        final Set<Author> authors = knownBook.getAuthors().stream()
                .map(author -> authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()))
                .collect(Collectors.toSet());

        Book book = bookRepository.findById(id).orElseThrow();

        book.setTitle(knownBook.getTitle());
        book.setAuthors(authors);
        bookRepository.save(book);
    }

    private void handleAuthorList(Set<NewAuthor> authors) {
        authors.stream()
                .filter(newPerson -> !authorRepository.existsByFirstNameAndLastName(newPerson.getFirstName(), newPerson.getLastName()))
                .forEach(newPerson -> authorRepository.save(authorMapper.toEntity(newPerson)));
    }

    public Page<Title> getListOfTitles(Pageable pageable) {
        return bookRepository
                .findAll(pageable)
                .map(book -> bookMapper.fromEntityToTitle(book));
    }

    @Cacheable("books")
    public BookDetails getBook(UUID id) {
        return bookRepository.findById(id)
                .map(book -> bookMapper.fromEntityToBookDetails(book))
                .orElse(null);
    }

    public Long countAllBooks() {
        return bookRepository.count();
    }
}
