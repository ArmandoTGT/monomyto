package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.criteria.Predicate;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers(String name, UUID id, LocalDate startDate, LocalDate endDate){

        if(id != null){
            Optional<Customer> customer = customerRepository.findById(id);
            if(customer.isPresent()){
                return Collections.singletonList(customer.get());
            }
            throw new RuntimeException("Não existe cliente com o Id informado");
        } else {
            return customerRepository.findAll(getCustomerQuery(name, startDate, endDate));
        }

    }

    public Specification<Customer> getCustomerQuery(String name, LocalDate startDate, LocalDate endDate){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("birthDate"), startDate, endDate));
            }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

