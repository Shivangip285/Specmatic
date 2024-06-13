package com.store;


import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class ProductId {
    private int id;

    public ProductId(int id) {
        this.id = id;
    }



}

