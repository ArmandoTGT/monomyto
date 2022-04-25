package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class SaleModel {

    @NotNull
    @JsonProperty("Id")
    private UUID id;

    @NotNull
    @JsonProperty("IdCliente")
    private UUID clientId;

    @NotNull
    @JsonProperty("Data")
    private LocalDateTime date;

    @Valid
    @JsonProperty("Itens")
    private List<SaleItemModel> saleItems = new ArrayList<>();
}
