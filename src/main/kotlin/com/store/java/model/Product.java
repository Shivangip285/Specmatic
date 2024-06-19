package com.store.java.model;


import lombok.Data;

@Data

public class Product extends ProductDetails {
    private int id;

    public Product(int id, String name, String type, int inventory,double cost) {
        super(name, type, inventory,cost);
        this.id=id;
    }




    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



