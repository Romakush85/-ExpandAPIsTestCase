package org.expandapis.testcase.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date entryDate;
    @Column(name="item_code")
    private String itemCode;
    @Column(name="item_name")
    private String itemName;
    @Column(name="item_quantity")
    private int itemQuantity;
    @Column(name="status", length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;
}
