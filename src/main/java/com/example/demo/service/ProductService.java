package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts(String name, UUID id, Long minAlcohol, Long maxAlcohol){

        if(id != null){
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()){
                return Collections.singletonList(product.get());
            }
            throw new RuntimeException("Não existe produto com o Id informado");
        } else {
            return productRepository.findAll(getProductQuery(name, minAlcohol, maxAlcohol));
        }

    }

    public Specification<Product> getProductQuery(String name, Long minAlcohol, Long maxAlcohol){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (minAlcohol != null && maxAlcohol != null) {
                predicates.add(criteriaBuilder.between(root.get("alcohol"), minAlcohol, maxAlcohol));
            }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
