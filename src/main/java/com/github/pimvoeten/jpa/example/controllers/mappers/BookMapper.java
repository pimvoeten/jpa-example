package com.github.pimvoeten.jpa.example.controllers.mappers;

import com.github.pimvoeten.jpa.example.controllers.requestmodels.NewBook;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.BookDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.Title;
import com.github.pimvoeten.jpa.example.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UUIDMapper.class, AuthorMapper.class})
public interface BookMapper {

    Title fromEntityToTitle(Book book);

    BookDetails fromEntityToBookDetails(Book book);

    Book toEntity(NewBook newBook);
}
