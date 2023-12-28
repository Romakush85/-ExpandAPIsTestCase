package org.expandapis.testcase.models;

import lombok.Data;

import java.util.Date;

@Data
public class ProductRecord {
    private String entryDate;
    private String itemCode;
    private String itemName;
    private String itemQuantity;
    private String status;

    public ProductRecord(String entryDate, String itemCode, String itemName, String itemQuantity, Status status) {
        this.entryDate = entryDate;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.status = status.toString();
    }
}
