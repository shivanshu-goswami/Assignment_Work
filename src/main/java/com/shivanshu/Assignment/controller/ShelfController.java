package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.dto.ShelfSummaryResponseDto;
import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.repository.ShelfRepository;
import com.shivanshu.Assignment.service.ShelfService;
import jakarta.validation.Valid;
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

    @PostMapping("/attach/{shelfId}/{shelfPositionId}")
    public Shelf attachExistingShelf(@PathVariable String shelfId,@PathVariable String shelfPositionId) {
        return shelfService.attachExistingShelf(shelfId,shelfPositionId);
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

    @GetMapping("/unattached")
    public List<Shelf> getUnattachedShelves() {
        return shelfService.getUnattachedShelves();
    }

    //update shelf
    @PutMapping("/{id}")
    public Shelf updateShelfById(@PathVariable String id,@Valid @RequestBody Shelf shelf) {
        return shelfService.updateShelf(id, shelf);
    }

    //delete Shelf
    @DeleteMapping("/{id}")
    public String deleteShelf(@PathVariable String id) {
        shelfService.deleteShelf(id);
        return "Shelf deleted successfully";
    }
    //get mapping for shelf summary
    @GetMapping("/{id}/summary")
    public ShelfSummaryResponseDto getShelfSummary(@PathVariable String id) {
        return shelfService.getShelfSummary(id);
    }
}
