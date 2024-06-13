package com.store;

import lombok.Data;

@Data

public class Product extends ProductDetails {
    private int id;

    public Product(int id, String name, String type, int inventory) {
        super(name, type, inventory);
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


