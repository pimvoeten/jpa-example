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
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
// Exclude many-to-many relationships, this will cause a recursive stackoverflow
@EqualsAndHashCode(exclude = {"books"})
@ToString(exclude = {"books"})
@Entity
@Table(indexes = {
        @Index(name = "uq_name", columnList = "lastName, firstName", unique = true)
})
@NamedEntityGraph(
        name = "author-with-books",
        attributeNodes = {
                @NamedAttributeNode("books")
        }

)
public class Author {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;
}
