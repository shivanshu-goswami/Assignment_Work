package com.shivanshu.Assignment.model;

import jakarta.validation.constraints.NotBlank;

public class Shelf {
    private String id;
    @NotBlank(message="Shelf name is required")
    private String shelfName;
    @NotBlank(message="Part number is required")
    private String partNumber;
    private Boolean isDeleted;

    public Shelf(){
    }

    public Shelf(String id, String shelfName, String partNumber, Boolean isDeleted) {
        this.id = id;
        this.shelfName = shelfName;
        this.partNumber = partNumber;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
