package com.github.pimvoeten.jpa.example.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
// Exclude many-to-many relationships, this will cause a recursive stackoverflow
@EqualsAndHashCode(exclude = {"authors"})
@ToString(exclude = {"authors"})
@Entity
@Table(indexes = {
        @Index(name = "uq_title", columnList = "title", unique = true)
})
@NamedEntityGraph(
        name = "book-with-authors",
        attributeNodes = {
                @NamedAttributeNode("authors")
        }
)
public class Book {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 13)
    private String isbn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_authors",
            joinColumns = {@JoinColumn(name = "books_id")},
            inverseJoinColumns = {@JoinColumn(name = "authors_id")}
    )
    private List<Author> authors;
}

/**
 * select authors0_.books_id as books_id1_2_0_, authors0_.authors_id as authors_2_2_0_, author1_.id as id1_0_1_, author1_.first_name as first_na2_0_1_, author1_.last_name as last_nam3_0_1_ from book_authors authors0_ inner join author author1_ on authors0_.authors_id=author1_.id where authors0_.books_id=?
 * select book0_.id as id1_1_0_, book0_.isbn as isbn2_1_0_, book0_.title as title3_1_0_ from book book0_ where book0_.id=?
 * <p>
 * <p>
 * select book0_.id as id1_1_0_, book0_.isbn as isbn2_1_0_, book0_.title as title3_1_0_, authors1_.books_id as books_id1_2_1_, author2_.id as authors_2_2_1_, author2_.id as id1_0_2_, author2_.first_name as first_na2_0_2_, author2_.last_name as last_nam3_0_2_ from book book0_ left outer join book_authors authors1_ on book0_.id=authors1_.books_id left outer join author author2_ on authors1_.authors_id=author2_.id where book0_.id=?
 */
