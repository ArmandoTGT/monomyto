package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "TB_SALE")
public class Sale {

    @Id
    @Column(name = "pk_id", nullable = false)
    private UUID id;

    @JoinColumn(name = "client_id", referencedColumnName = "pk_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SaleItem> saleItems = new ArrayList<>();
}
