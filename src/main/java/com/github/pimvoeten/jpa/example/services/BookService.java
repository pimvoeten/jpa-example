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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
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
        final List<Author> authors = handleAuthorsList(newBook.getAuthors());

        final Book book = bookMapper.toEntity(newBook);
        book.setAuthors(authors);
        bookRepository.save(book);

        return Optional.of(bookMapper.fromEntityToTitle(book));
    }

    /**
     * Handle the list of authors (create new or retrieve known)
     */
    private List<Author> handleAuthorsList(List<NewAuthor> newAuthors) {
        return newAuthors.parallelStream()
                .map(this::fetchOrCreateAuthor)
                .map(author -> {
                    if (author.getId() == null) {
                        return authorRepository.save(author);
                    }
                    return author;
                })
                .collect(Collectors.toList());
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

        // Handle the list of authors (create new or retrieve known)
        final List<Author> authors = handleAuthorsList(knownBook.getAuthors());

        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(knownBook.getTitle());
        book.setAuthors(authors);
        bookRepository.save(book);
    }

    private Author fetchOrCreateAuthor(NewAuthor newAuthor) {
        return authorRepository.findByFirstNameAndLastName(newAuthor.getFirstName(), newAuthor.getLastName())
                .orElse(createNewAuthor(newAuthor));
    }

    private Author createNewAuthor(NewAuthor newAuthor) {
        Author author = new Author();
        author.setFirstName(newAuthor.getFirstName());
        author.setLastName(newAuthor.getLastName());
        return author;
    }

    public Page<Title> getListOfTitles(Pageable pageable) {
        return bookRepository
                .findAll(pageable)
                .map(book -> bookMapper.fromEntityToTitle(book));
    }

    //    @Cacheable("books")
    public BookDetails getBook(UUID id) {
        return bookRepository.findById(id)
                .map(book -> bookMapper.fromEntityToBookDetails(book))
                .orElse(null);
    }

    //    @Cacheable("books")
    public BookDetails getBookWithAuthors(UUID id) {
        return bookMapper
                .fromEntityToBookDetails(
                        bookRepository.findWithGraph(id, "book-with-authors")
                );
    }

    public BookDetails getBookWithAuthorsNamed(UUID id) {
        return bookMapper
                .fromEntityToBookDetails(
                        bookRepository.getById(id)
                );
    }

    public Long countAllBooks() {
        return bookRepository.count();
    }

}
