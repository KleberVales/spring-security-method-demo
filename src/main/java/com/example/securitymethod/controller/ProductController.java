package com.example.securitymethod.controller;

import com.example.securitymethod.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestParam String name) {
        return service.createProduct(name);
    }

    @GetMapping
    public List<String> list() {
        return service.listProducts();
    }
}
