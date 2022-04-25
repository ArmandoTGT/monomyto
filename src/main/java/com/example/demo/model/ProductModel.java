package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class ProductModel {

    @NotNull
    @JsonProperty("Id")
    private UUID id;

    @NotBlank
    @JsonProperty("Marca")
    private String brand;

    @NotBlank
    @JsonProperty("Classificacao")
    private String classification;

    @NotBlank
    @JsonProperty("Nome")
    private String name;

    @NotBlank
    @JsonProperty("TeorAlcoolico")
    private Long alcohol;

    @NotNull
    @JsonProperty("Regiao")
    private String region;

    @NotNull
    @JsonProperty("PrecoAtual")
    private Double currentPrice;
}
