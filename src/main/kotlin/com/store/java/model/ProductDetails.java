package com.store.java.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetails {

    private String name;
    private String type;
    private int inventory;
    private double cost;

}

