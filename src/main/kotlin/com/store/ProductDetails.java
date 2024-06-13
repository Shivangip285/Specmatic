package com.store;



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




}

//http://modelmapper.org/user-manual/configuration/#matching-strategies
//https://www.baeldung.com/java-modelmapper
