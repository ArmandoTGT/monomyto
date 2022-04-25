package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class SaleItemModel {

    @NotNull
    @JsonProperty("Id")
    private UUID productId;

    @NotNull
    @JsonProperty("PrecoUnitario")
    private Double unitPrice;

    @NotNull
    @JsonProperty("Quantidade")
    private Long amount;

}
