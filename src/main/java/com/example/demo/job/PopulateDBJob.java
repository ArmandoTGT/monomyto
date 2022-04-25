package com.example.demo.job;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.entity.SaleItem;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.ProductModel;
import com.example.demo.model.SaleModel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleRepository;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PopulateDBJob {

    @Value("classpath:json/Catalogo.json")
    Resource resourceCatalogo;

    @Value("classpath:json/Clientes.json")
    Resource resourceClientes;

    @Value("classpath:json/Vendas.json")
    Resource resourceVendas;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SaleRepository saleRepository;

    @PostConstruct
    private void populateDbAfterInitialize() throws IOException {
        ObjectMapper mapper = JsonMapper
                .builder()
                .findAndAddModules()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();

        List<ProductModel> productModels = Arrays.asList(mapper.readValue(resourceCatalogo.getInputStream(), ProductModel[].class));
        saveProducts(productModels);

        List<CustomerModel> customerModels = Arrays.asList(mapper.readValue(resourceClientes.getInputStream(), CustomerModel[].class));
        saveCustomers(customerModels);

        List<SaleModel> saleModels = Arrays.asList(mapper.readValue(resourceVendas.getInputStream(), SaleModel[].class));
        saveSales(saleModels);

    }

    private void saveProducts(List<ProductModel> productModels){
        List<Product> products = new ArrayList<>();

        for (ProductModel productModel : productModels){
            Product product = new Product();
            product.setId(productModel.getId());
            product.setBrand(productModel.getBrand());
            product.setClassification(productModel.getClassification());
            product.setName(productModel.getName());
            product.setAlcohol(productModel.getAlcohol());
            product.setRegion(productModel.getRegion());
            product.setCurrentPrice(productModel.getCurrentPrice());

            products.add(product);
        }

        productRepository.saveAll(products);
    }

    private void saveCustomers(List<CustomerModel> customerModels){
        List<Customer> customers = new ArrayList<>();

        for (CustomerModel customerModel : customerModels){
            Customer customer = new Customer();
            customer.setId(customerModel.getId());
            customer.setName(customerModel.getName());
            customer.setBirthDate(customerModel.getBirthDate());

            customers.add(customer);
        }

        customerRepository.saveAll(customers);
    }

    private void saveSales(List<SaleModel> saleModels){
        List<Sale> sales = new ArrayList<>();

        for (SaleModel saleModel : saleModels){
            Sale sale = new Sale();
            List<SaleItem> saleItems = new ArrayList<>();
            sale.setId(saleModel.getId());
            sale.setCustomer(customerRepository.getById(saleModel.getClientId()));
            sale.setDate(saleModel.getDate());
            saleModel.getSaleItems().stream().forEach(x -> {
                SaleItem saleItem = new SaleItem();
                saleItem.setProduct(productRepository.getById(x.getProductId()));
                saleItem.setUnitPrice(x.getUnitPrice());
                saleItem.setAmount(x.getAmount());
                saleItem.setSale(sale);

                saleItems.add(saleItem);
            });
            sale.setSaleItems(saleItems);


            sales.add(sale);
        }

        saleRepository.saveAll(sales);
    }
}
