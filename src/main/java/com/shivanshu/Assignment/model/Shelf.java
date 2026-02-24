package com.shivanshu.Assignment.model;

public class Shelf {
    private String id;
    private String shelfName;
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
