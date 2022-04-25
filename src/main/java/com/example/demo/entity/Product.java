package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "TB_PRODUCT")
public class Product {

    @Id
    @Column(name = "pk_id", nullable = false)
    private UUID id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "classification", nullable = false)
    private String classification;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alcohol", nullable = false)
    private Long alcohol;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;
}
