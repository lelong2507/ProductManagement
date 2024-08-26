package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> showList() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public void deleteProduct(int idProduct) {
        Optional<Product> product = productRepository.findById(idProduct);
        if (product.isPresent()) {
            Product getProduct = product.get();
            productRepository.delete(getProduct);
        }
    }

    // @Override
    // public Product updateProduct(int idProduct, Product product) {
    //     Optional<Product> getProduct = productRepository.findById(idProduct);
    //     if (getProduct.isPresent()) {
    //         product = getProduct.get();
            
    //     }
    // }

}
