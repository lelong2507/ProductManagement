package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.FileUtils;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileUtils fileUtils;

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

    @Override
    public Product findById(int idProduct) {
        Optional<Product> product = productRepository.findById(idProduct);
        if (product.isPresent()) {
            Product getProduct = product.get();
            return getProduct;
        }
        return null;
    }

    @Override
    public Product updateProduct(int idProduct, Product product, MultipartFile file) {
        Optional<Product> getProduct = productRepository.findById(idProduct);
        if (getProduct.isPresent()) {
            Product existingProduct = getProduct.get();

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            try {
                fileUtils.validateFile(file);
                existingProduct.setImg(file.getBytes());
            } catch (Exception e) {
                System.out.println(e);
            }

            return product;

        }

        return null;
    }

}
