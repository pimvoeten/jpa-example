package com.github.pimvoeten.jpa.example.controllers.mappers;

import com.github.pimvoeten.jpa.example.controllers.requestmodels.NewAuthor;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorDetails;
import com.github.pimvoeten.jpa.example.controllers.responsemodels.AuthorShort;
import com.github.pimvoeten.jpa.example.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface AuthorMapper {

    Author toEntity(NewAuthor person);

    AuthorShort fromEntityToAuthor(Author author);

    AuthorDetails fromEntityToAuthorDetails(Author author);
}
