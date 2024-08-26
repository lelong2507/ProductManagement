package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;

public interface ProductService {
    void addProduct(Product product);

    List<Product> showList();

    void deleteProduct(int idProduct);

    Product updateProduct(int idProduct, Product product, MultipartFile file);

    Product findById(int idProduct);
}
