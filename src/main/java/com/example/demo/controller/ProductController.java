package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.utils.FileUtils;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private FileUtils fileUtils;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add-form")
    public String handleShowFormAdd(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "components/add-product";
    }

    @GetMapping("/")
    public String handleShowList(Model model) {
        List<Product> products = productService.showList();
        model.addAttribute("productList", products);

        return "index";
    }

    @PostMapping("/addProduct")
    public String handleAddProduct(@ModelAttribute("product") Product product,
            @RequestParam("file") MultipartFile file) {
        try {
            fileUtils.validateFile(file);
            product.setImg(file.getBytes());
            productService.addProduct(product);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/product/add-form";
    }

}
