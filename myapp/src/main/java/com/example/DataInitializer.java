package com.example;

import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insert 10 categories
        IntStream.range(1, 11).forEach(i -> {
            Category category = new Category();
            category.setName("Category " + i);
            category.setCreatedAt(LocalDateTime.now());
            categoryRepository.save(category);
        });

        // Insert 10 products, associating each product with a category
        IntStream.range(1, 11).forEach(i -> {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(10.0 + i); // Example price
            product.setCategory(categoryRepository.findById((long) (i % 10 + 1)).orElseThrow());
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        });
    }
}