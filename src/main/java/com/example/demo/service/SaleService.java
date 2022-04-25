package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.model.CountSumSaleModel;
import com.example.demo.model.SaleByCustomerModel;
import com.example.demo.model.SaleByProductModel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleItemRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.utils.Order;
import com.example.demo.utils.OrderProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    SaleItemRepository saleItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Page<Sale> getSales(String customerName, String productName, LocalDateTime startDate, LocalDateTime endDate, int page, int size){

        return saleRepository.findAll(getSaleQuery(customerName, productName, startDate, endDate), PageRequest.of(page, size));
    }

    public Specification<Sale> getSaleQuery(String customerName, String productName, LocalDateTime startDate, LocalDateTime endDate){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customerName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("name")), "%" + customerName.toLowerCase() + "%"));
            }

            if (productName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.joinList("saleItems").get("product").get("name")), "%" + productName.toLowerCase() + "%"));
            }

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
            }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<SaleByProductModel> getSalesByProducts(OrderProperty orderProperty, Order order) {

        List<Product> products = productRepository.findAll();
        List<SaleByProductModel> saleByProductModels = new ArrayList<>();

        for (Product product : products){
            SaleByProductModel saleByProductModel =  new SaleByProductModel();
            CountSumSaleModel countSumSaleModel = saleItemRepository.countSumSalesByProduct(product);
            saleByProductModel.setProductId(product.getId());
            saleByProductModel.setProductName(product.getName());
            saleByProductModel.setSalesCount(countSumSaleModel.getSalesCount());
            saleByProductModel.setSalesAmount(countSumSaleModel.getSalesAmount());
            saleByProductModel.setSalesValue(BigDecimal.valueOf(countSumSaleModel.getSalesValue()).setScale(2, RoundingMode.HALF_UP).doubleValue());

            saleByProductModels.add(saleByProductModel);
        }

        if(orderProperty != null && orderProperty.equals(OrderProperty.VALOR_TOTAL)){
            if(order != null && order.equals(Order.DECRESCENTE)){
                Collections.sort(saleByProductModels, Comparator.comparingDouble(SaleByProductModel::getSalesValue).reversed());
            } else{
                Collections.sort(saleByProductModels, Comparator.comparingDouble(SaleByProductModel::getSalesValue));
            }
        }
        if(orderProperty != null && orderProperty.equals(OrderProperty.QUANTIDADE_TOTAL)){
            if(order != null && order.equals(Order.DECRESCENTE)){
                Collections.sort(saleByProductModels, Comparator.comparingDouble(SaleByProductModel::getSalesAmount).reversed());
            } else{
                Collections.sort(saleByProductModels, Comparator.comparingDouble(SaleByProductModel::getSalesAmount));
            }
        }

        return saleByProductModels;
    }

    public List<SaleByCustomerModel> getSalesByCustomers(LocalDateTime startDate, LocalDateTime endDate, Long maxItem) {
            List<Customer> customers = customerRepository.findAll();
            List<SaleByCustomerModel> saleByCustomerModels = new ArrayList<>();

            for (Customer customer: customers){
                List<SaleByProductModel> saleByProductModels = new ArrayList<>();
                SaleByCustomerModel saleByCustomerModel = new SaleByCustomerModel();
                List<Product> products = productRepository.findAll();
                Long allSalesCount = 0l;
                for (Product product : products) {
                    SaleByProductModel saleByProductModel =  new SaleByProductModel();
                    CountSumSaleModel countSumSaleModel;
                    if(startDate != null && endDate != null) {
                        countSumSaleModel = saleItemRepository.countSumSalesByProductCustomerAndBetween(product, customer, startDate, endDate);
                    } else {
                        countSumSaleModel = saleItemRepository.countSumSalesByProductAndCustomer(product, customer);
                    }

                    saleByProductModel.setProductId(product.getId());
                    saleByProductModel.setProductName(product.getName());
                    saleByProductModel.setSalesCount(countSumSaleModel.getSalesCount());
                    if(countSumSaleModel.getSalesAmount() != null) {
                        saleByProductModel.setSalesAmount(countSumSaleModel.getSalesAmount());
                        saleByProductModel.setSalesValue(BigDecimal.valueOf(countSumSaleModel.getSalesValue()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    } else {
                        saleByProductModel.setSalesAmount(0l);
                        saleByProductModel.setSalesValue(0d);
                    }

                    allSalesCount += countSumSaleModel.getSalesCount();
                    saleByProductModels.add(saleByProductModel);
                }


                saleByCustomerModel.setCustomerId(customer.getId());
                saleByCustomerModel.setCustomerName(customer.getName());
                saleByCustomerModel.setSalesAmount(allSalesCount);
                Collections.sort(saleByProductModels, Comparator.comparingDouble(SaleByProductModel::getSalesAmount).reversed());
                saleByCustomerModel.setSaleItems(saleByProductModels.stream().limit(maxItem).collect(Collectors.toList()));

                saleByCustomerModels.add(saleByCustomerModel);
            }

        return saleByCustomerModels;
    }
}
