package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class CustomerModel {

    @NotNull
    @JsonProperty("Id")
    private UUID id;

    @NotBlank
    @JsonProperty("Nome")
    private String name;

    @NotNull
    @JsonProperty("DataNascimento")
    private LocalDate birthDate;


    public void setBirthDate(String birthDate) {
        LocalDate localDate;
        if(birthDate.contains("/")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            localDate = LocalDate.parse(birthDate, formatter);
        } else {
            localDate = LocalDate.parse(birthDate);
        }
        this.birthDate = localDate;
    }
}
