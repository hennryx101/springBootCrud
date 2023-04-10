package com.caterpillar.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public List<Product> getProduct(){
        return productRepo.findAll();
    }

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    public void deleteProduct(Integer id){
        productRepo.deleteById(id);
    }

    public Product findProduct(Integer id){
        return productRepo.findById(id).orElse(null);
    }
}
