package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.model.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository {
    Shelf createShelf(String ShelfPositionId ,Shelf shelf);
    Optional<Shelf> findShelfById(String id);
    List<Shelf> findAll();
    void softDelete(String id);
}
