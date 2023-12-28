package org.expandapis.testcase.models;

import lombok.Data;

import java.util.List;
@Data
public class ProductsRequest {
    private List<Product> records;
}
