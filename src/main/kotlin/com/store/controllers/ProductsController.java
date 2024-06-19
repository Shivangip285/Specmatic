package com.store.controllers;
import com.store.java.model.ErrorResponseBody;
import com.store.java.model.Product;
import com.store.java.model.ProductDetails;
import com.store.java.model.ProductId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ProductsController {

    private final List<Product> products = new ArrayList<>();
    private int currentId = 1;
    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestParam(required = false) String type) {
        if (type != null && !List.of("gadget", "book", "food", "other").contains(type)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseBody(
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                            HttpStatus.BAD_REQUEST.value(),
                            "Invalid product type",
                            "/products"
                    )
            );
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> type == null || product.getType().equals(type))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDetails productDetails) {
        if (isInvalidProductDetails(productDetails)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseBody(
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                            HttpStatus.BAD_REQUEST.value(),
                            "Invalid product details",
                            "/products"
                    )
            );
        }

        Product product = new Product(currentId++, productDetails.getName(), productDetails.getType(), productDetails.getInventory(), productDetails.getCost());
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductId(product.getId()));
    }

    private boolean isInvalidProductDetails(ProductDetails productDetails) {
        return productDetails.getName() == null || productDetails.getName().isEmpty() ||
                productDetails.getInventory() <= 0 || productDetails.getInventory() > 100 ||
                productDetails.getType() == null || productDetails.getType().isEmpty() ||
                productDetails.getName().matches(".*\\d.*") ||
                List.of("true", "false").contains(productDetails.getName()) ||
                !List.of("gadget", "book", "food", "other").contains(productDetails.getType()) ||
                productDetails.getCost() <= 0.0;
    }
}