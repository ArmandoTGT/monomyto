package com.example.demo.controller;

import com.example.demo.entity.Sale;
import com.example.demo.model.SaleByCustomerModel;
import com.example.demo.model.SaleByProductModel;
import com.example.demo.service.SaleService;
import com.example.demo.utils.Order;
import com.example.demo.utils.OrderProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SaleController {

    @Autowired
    SaleService saleService;

    @GetMapping("/vendas")
    public ResponseEntity<Page<Sale>> getSales(String customerName, String productName,
                                               @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                               @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {

        return new ResponseEntity<>(saleService.getSales(customerName, productName, startDate, endDate, page, size), HttpStatus.OK);
    }

    @GetMapping("/vendas-por-produtos")
    public ResponseEntity<List<SaleByProductModel>> getSalesByProducts(@RequestParam(required = false) OrderProperty orderProperty,
                                                                       @RequestParam(required = false) Order order) {

        return new ResponseEntity<>(saleService.getSalesByProducts(orderProperty, order), HttpStatus.OK);

    }

    @GetMapping("/vendas-por-clientes")
    public ResponseEntity<List<SaleByCustomerModel>> getSalesByCustomers(@DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                         @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                         @RequestParam(defaultValue = "1") Long maxItem) {

        return new ResponseEntity<>(saleService.getSalesByCustomers(startDate, endDate, maxItem), HttpStatus.OK);

    }

}