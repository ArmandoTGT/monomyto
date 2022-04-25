package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SaleByCustomerModel {


    private UUID customerId;

    private String customerName;

    private Long salesAmount;

    private List<SaleByProductModel> saleItems = new ArrayList<>();
}
