package com.CodeVector.DemoTask.Controller;

import com.CodeVector.DemoTask.DTO.ProductResponseDTO;
import com.CodeVector.DemoTask.Enums.Category;
import com.CodeVector.DemoTask.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*")
public class Controller {

    @Autowired
    private ProductService productService;

    @PostMapping("/seeding")
    private String seeder() throws SQLException, ClassNotFoundException {
        return productService.seed();
    }
    @GetMapping("/products")
    public ResponseEntity<ProductResponseDTO> findProducts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Category category)
            throws SQLException, ClassNotFoundException {

        ProductResponseDTO response = productService.findPage(cursor, limit, category);
        return ResponseEntity.ok(response);
    }
}
