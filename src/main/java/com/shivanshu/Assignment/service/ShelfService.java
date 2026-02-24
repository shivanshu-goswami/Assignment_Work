package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.model.Shelf;

import java.util.List;

public interface ShelfService {
    Shelf createShelf(String shelfPositionId,Shelf shelf);
    Shelf getShelfById(String id);
    List<Shelf> getAllShelves();
    void deleteShelf(String id);
}
