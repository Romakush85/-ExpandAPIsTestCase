package org.expandapis.testcase.services;

import org.expandapis.testcase.models.Product;
import org.expandapis.testcase.models.ProductRecord;
import org.expandapis.testcase.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public void addAll(List<Product> records) {
        productsRepository.saveAll(records);
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public ProductRecord convertTo(Product product) {
        return new  ProductRecord(
                product.getEntryDate().toString(),
                product.getItemCode(),
                product.getItemName(),
                String.valueOf(product.getItemQuantity()),
                product.getStatus()
        );
    }
}
