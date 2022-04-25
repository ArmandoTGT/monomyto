package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SaleByProductModel {


    private UUID productId;

    private String productName;

    private Long salesCount;

    private Long salesAmount;

    private Double salesValue;
}
