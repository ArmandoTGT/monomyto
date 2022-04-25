package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/produtos")
    public ResponseEntity<List<Product>> getProducts(String name, @RequestParam(required = false) UUID id,
                                                     Long minAlcohol,
                                                     Long maxAlcohol) {

        return new ResponseEntity<>(productService.getProducts(name, id, minAlcohol, maxAlcohol), HttpStatus.OK);
    }

}