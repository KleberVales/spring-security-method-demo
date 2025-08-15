package com.example.securitymethod.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(String name) {
        return "Produto criado: " + name;
    }

    @PreAuthorize("isAuthenticated()")
    public List<String> listProducts() {
        return List.of("Notebook", "Mouse", "Teclado");
    }
}
