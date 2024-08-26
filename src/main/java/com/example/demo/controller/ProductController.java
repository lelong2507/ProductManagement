package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.utils.FileUtils;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/")
    public String handleShowList(Model model) {
        List<Product> products = productService.showList();
        for (Product product : products) {
            if (product.getImg() != null) {
                String base64Image = Base64Utils.encodeToString(product.getImg());
                product.setImageBase64(base64Image);
            }
        }
        model.addAttribute("productList", products);
        return "index";
    }

    @GetMapping("/add-form")
    public String handleShowFormAdd(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "components/add-product";
    }

    @GetMapping("/delete/{id}")
    public String handleDeleteProduct(@PathVariable("id") String id) {
        try {
            int idProduct = Integer.parseInt(id);
            productService.deleteProduct(idProduct);
        } catch (Exception e) {
            System.out.println("Not valid id: " + id);
        }

        return "redirect:/product/";
    }

    @GetMapping("/update/{id}")
    public String handleShowFormUpdate(@PathVariable("id") int id, Model model, HttpSession session) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        session.setAttribute("id", product.getId());
        return "components/update-form";
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

        return "redirect:/product/";
    }

    @PostMapping("/updateProduct/{id}")
    public String postMethodName(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file,
            @PathVariable("id") String idProduct) {
        int id = Integer.parseInt(idProduct);
        Product getProductUpdate = productService.findById(id);
        try {

            fileUtils.validateFile(file);
            productService.updateProduct(id, getProductUpdate, file);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/product/";
    }

}
