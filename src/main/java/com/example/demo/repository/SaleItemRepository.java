package com.example.demo.repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.SaleItem;
import com.example.demo.model.CountSumSaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID>, JpaSpecificationExecutor<SaleItem> {

    @Query(value = "SELECT new com.example.demo.model.CountSumSaleModel(COUNT(s), SUM(s.amount), cast(SUM(s.unitPrice * s.amount) as double)) "
            + "FROM SaleItem as s WHERE s.product = :product")
    CountSumSaleModel countSumSalesByProduct(Product product);

    @Query(value = "SELECT new com.example.demo.model.CountSumSaleModel(COUNT(s), SUM(s.amount), cast(SUM(s.unitPrice * s.amount) as double)) "
            + "FROM SaleItem as s WHERE s.product = :product AND s.sale.customer = :customer")
    CountSumSaleModel countSumSalesByProductAndCustomer(Product product, Customer customer);

    @Query(value = "SELECT new com.example.demo.model.CountSumSaleModel(COUNT(s), SUM(s.amount), cast(SUM(s.unitPrice * s.amount) as double)) "
            + "FROM SaleItem as s WHERE s.product = :product AND s.sale.customer = :customer AND s.sale.date BETWEEN :startDate AND :endDate")
    CountSumSaleModel countSumSalesByProductCustomerAndBetween(Product product, Customer customer, LocalDateTime startDate, LocalDateTime endDate);

}
