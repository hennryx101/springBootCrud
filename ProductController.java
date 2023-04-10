package com.caterpillar.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String getproduct(Model model){
        model.addAttribute("product", productRepository.findAll());
        return "index";
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProduct(@RequestBody Product product){

        Product save = productService.saveProduct(product);
        if(save == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving Product");
        }
        return ResponseEntity.ok("Successfully added new Product");
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/update/{id}")
    public String getUpdateProduct(@PathVariable("id") String id, Model model){
        model.addAttribute("product", productService.findProduct(Integer.parseInt(id)));
        return "update";
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable() String id, @RequestBody Product product){

        Product ExistingProduct = productService.findProduct(Integer.parseInt(id));

        if(ExistingProduct == null){
            return ResponseEntity.notFound().build();
        }

        ExistingProduct.setProduct(product.getProduct());
        ExistingProduct.setPrice(product.getPrice());
        productService.saveProduct(ExistingProduct);
        return ResponseEntity.ok(ExistingProduct);
    }
}
