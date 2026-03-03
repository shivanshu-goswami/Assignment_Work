package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.ShelfNotFoundException;
import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShelfServiceImpl implements ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfServiceImpl(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }
    //create shelf logic
    @Override
    public Shelf createShelf(String shelfPositionId,Shelf shelf) {
       if(shelf.getShelfName()==null || shelf.getShelfName().isBlank()){
           throw new IllegalArgumentException("Shelf name is required");
       }
        if(shelf.getPartNumber()==null || shelf.getPartNumber().isBlank()){
            throw new IllegalArgumentException("Part Number is required");
        }
        //now will generate ID for shelf and will set it as not-deleted
        shelf.setId(UUID.randomUUID().toString());
        shelf.setDeleted(false);
        return shelfRepository.createShelf(shelfPositionId,shelf);
    }

    //read one shelf using id
    @Override
    public Shelf getShelfById(String id) {
        return shelfRepository.findShelfById(id).orElseThrow(()->new ShelfNotFoundException("Shelf not found with id "+id));
    }
    @Override
    public Optional<Shelf> findShelfByShelfPositionId(String shelfPositionId) {
        return shelfRepository.findShelfById(shelfPositionId);
    }

    //read all shelves
    @Override
    public List<Shelf> getAllShelves(){
        return shelfRepository.findAll();
    }

    @Override
    public Shelf updateShelf(String shelfPositionId,Shelf shelf) {
        //not needed these checks here as it will be handled by getShelfById below
        if(shelfPositionId==null ||shelfPositionId.isBlank() ){
            throw new IllegalArgumentException("Shelf position id is required");
        }
        //it checks if shelf exist with this id or not and throws exception if not
        getShelfById(shelfPositionId);
        //now can call this safely as we have checked shelf existence so can update now without any error or exception
        return shelfRepository.updateShelf(shelfPositionId,shelf);
    }
    //delete
    @Override
    public void deleteShelf(String id) {
        //it will ensure that shelf exist, if not exist then getShelf will itself throw error otherwise delete will be called
        getShelfById(id);
        shelfRepository.softDelete(id);
    }
}
