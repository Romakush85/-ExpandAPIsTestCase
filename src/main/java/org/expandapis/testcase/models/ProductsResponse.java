package org.expandapis.testcase.models;

import lombok.Data;

import java.util.List;

@Data
public class ProductsResponse {
    private List<ProductRecord> records;

    public ProductsResponse(List<ProductRecord> records) {
        this.records = records;
    }
}
