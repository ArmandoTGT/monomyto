package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.entity.SaleItem;
import com.example.demo.model.CountSumSaleModel;
import com.example.demo.model.SaleByCustomerModel;
import com.example.demo.model.SaleByProductModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class DefaultClassesCreator {

    public static SaleByCustomerModel createDefaultSalesByCustomerModel(){
        SaleByCustomerModel saleByCustomerModel =  new SaleByCustomerModel();
        SaleByProductModel saleByProductModel =  createDefaultSaleByProductModel();
        saleByCustomerModel.setCustomerId(UUID.randomUUID());
        saleByCustomerModel.setCustomerName(("Ricardo Pieper"));
        saleByCustomerModel.setSalesAmount(1l);
        saleByCustomerModel.setSaleItems(Arrays.asList(saleByProductModel));

        return saleByCustomerModel;
    }

    public static SaleByProductModel createDefaultSaleByProductModel(){
        SaleByProductModel saleByProductModel =  new SaleByProductModel();
        saleByProductModel.setProductId(UUID.randomUUID());
        saleByProductModel.setProductName("Canuto Golden 700ml");
        saleByProductModel.setSalesCount(1l);
        saleByProductModel.setSalesAmount(1l);
        saleByProductModel.setSalesValue(11.9);

        return saleByProductModel;
    }

    public static CountSumSaleModel createDefaultCountSumSaleModel(){
        CountSumSaleModel countSumSaleModel = new CountSumSaleModel();
        countSumSaleModel.setSalesCount(1l);
        countSumSaleModel.setSalesAmount(1l);
        countSumSaleModel.setSalesValue(11.9);

        return countSumSaleModel;
    }

    public static SaleItem createDefaultSaleItem(){
        SaleItem saleItem = new SaleItem();
        saleItem.setProduct(createDefaultProduct());
        saleItem.setUnitPrice(11.9);
        saleItem.setAmount(1l);
        saleItem.setSale(createDefaultSale(saleItem));

        return saleItem;
    }

    public static Sale createDefaultSale(SaleItem saleItem){
        Sale sale = new Sale();
        sale.setId(UUID.randomUUID());
        sale.setCustomer(createDefaultCustomer());
        sale.setDate(LocalDateTime.parse("2010-01-03T11:31:51"));
        sale.setSaleItems(Arrays.asList(saleItem));

        return sale;
    }

    public static Customer createDefaultCustomer(){
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Ricardo Pieper");
        customer.setBirthDate(LocalDate.parse("1994-07-15"));

        return customer;
    }

    public static Product createDefaultProduct(){
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setBrand("Cachaças Canuto");
        product.setClassification("Ouro");
        product.setName("Canuto Golden 700ml");
        product.setAlcohol(44l);
        product.setRegion("MG");
        product.setCurrentPrice(110.00);

        return product;
    }
}
