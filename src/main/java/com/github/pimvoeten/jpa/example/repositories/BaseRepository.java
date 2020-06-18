package com.github.pimvoeten.jpa.example.repositories;

public interface BaseRepository<D, T> {
    D findWithGraph(T id, String graphName);
}
