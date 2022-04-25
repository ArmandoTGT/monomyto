package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Customer>> getCustomers(String name, @RequestParam(required = false) UUID id,
                                                       @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                       @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return new ResponseEntity<>(customerService.getCustomers(name, id, startDate, endDate), HttpStatus.OK);
    }

}