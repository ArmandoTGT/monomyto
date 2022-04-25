package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "TB_ITEM_SALE")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "product_id", referencedColumnName = "pk_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "fk_sale", nullable = false)
    @JsonBackReference
    private Sale sale;
}
