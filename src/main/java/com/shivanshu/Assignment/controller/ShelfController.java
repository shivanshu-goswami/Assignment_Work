package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.repository.ShelfRepository;
import com.shivanshu.Assignment.service.ShelfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelves")
public class ShelfController {
    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }
    //create
    @PostMapping("/{shelfPositionId}")
    public Shelf createShelf(@PathVariable String shelfPositionId, @RequestBody Shelf shelf) {
        return shelfService.createShelf(shelfPositionId, shelf);
    }
    //read all
    @GetMapping
    public List<Shelf> getAllShelves() {
        return shelfService.getAllShelves();
    }

    //read one shelf
    @GetMapping("/{id}")
    public Shelf getShelfById(@PathVariable String id) {
        return shelfService.getShelfById(id);
    }

    //delete Shelf
    @DeleteMapping("/{id}")
    public String deleteShelf(@PathVariable String id) {
        shelfService.deleteShelf(id);
        return "Shelf deleted successfully";
    }
}
