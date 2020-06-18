package com.github.pimvoeten.jpa.example.repositories;

import com.github.pimvoeten.jpa.example.entities.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BaseRepository<Book, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book findWithGraph(UUID id, String graphName) {
        EntityGraph entityGraph = entityManager.getEntityGraph(graphName);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        Book book = entityManager.find(Book.class, id, properties);
        return book;
    }
}
