package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.model.ShelfPosition;
import com.shivanshu.Assignment.service.ShelfPositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelf-positions")
public class ShelfPositionController {
    private final ShelfPositionService shelfPositionService;

    public ShelfPositionController(ShelfPositionService shelfPositionService) {
        this.shelfPositionService = shelfPositionService;
    }
    //Read
    @GetMapping("/{id}")
    public ShelfPosition getById(@PathVariable String id){
        return shelfPositionService.getById(id);
    }
    //read through device id
    @GetMapping("/device/{deviceId}")
    public List<ShelfPosition> getByDeviceId(@PathVariable String deviceId){
        return shelfPositionService.getByDeviceId(deviceId);
    }
    //read all
    @GetMapping
    public List<ShelfPosition> getAll(){
        return shelfPositionService.getAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        shelfPositionService.delete(id);
        return "ShelfPosition deleted successfully";
    }
}
