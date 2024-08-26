package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;

public interface ProductService {
    void addProduct(Product product);

    List<Product> showList();

    void deleteProduct(int idProduct);
    // Product updateProduct(int idProduct, Product product);
}
