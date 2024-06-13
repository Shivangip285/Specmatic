package com.store.controllers;


import com.store.ErrorResponseBody;
import com.store.Product;
import com.store.ProductId;
import com.store.ProductDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Integer, Product> productMap = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDetails productDetails) {
        if (productDetails.getName() == null || productDetails.getType() == null || productDetails.getInventory() < 0) {
            return new ResponseEntity<>(new ErrorResponseBody(LocalDateTime.now(), 400, "Invalid request data", "/products"), HttpStatus.BAD_REQUEST);
        }

        int id = idCounter.incrementAndGet();
        Product product = new Product(id,productDetails.getName(),productDetails.getType(),productDetails.getInventory());
        product.setId(id);
        product.setName(productDetails.getName());
        product.setType(productDetails.getType());
        product.setInventory(productDetails.getInventory());

        productMap.put(id, product);

        return new ResponseEntity<>(new ProductId(id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getProductsByType(@RequestParam String type) {
        if (type == null || type.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponseBody(LocalDateTime.now(), 400, "Invalid type parameter", "/products"), HttpStatus.BAD_REQUEST);
        }
         try{
             List<Product> products = productMap.values().stream()
                     .filter(product -> type.equals(product.getType()))
                     .collect(Collectors.toList());
             return new ResponseEntity<>(products, HttpStatus.OK);
         }
        catch(HttpMessageNotReadableException e){
            return new ResponseEntity<>(new ErrorResponseBody(LocalDateTime.now(), 400, "Invalid type parameter", "/products"), HttpStatus.BAD_REQUEST);

        }

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseBody> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponseBody errorResponse = new ErrorResponseBody(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid type parameter", ex.getParameter().getParameterName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}