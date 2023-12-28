package org.expandapis.testcase.controllers;

import org.expandapis.testcase.models.Product;
import org.expandapis.testcase.models.ProductRecord;
import org.expandapis.testcase.models.ProductsRequest;
import org.expandapis.testcase.models.ProductsResponse;
import org.expandapis.testcase.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;
    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addProducts(@RequestBody ProductsRequest records) {
        productsService.addAll(records.getRecords());
        return ResponseEntity.ok("Products added successfully");
    }


    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllProducts() {
        List<Product> products = productsService.findAll();
        List<ProductRecord> records = new ArrayList<>();
        for(Product p : products) {
            records.add(productsService.convertTo(p));
        }
        ProductsResponse response = new ProductsResponse(records);
        return ResponseEntity.ok(response);
    }

}
