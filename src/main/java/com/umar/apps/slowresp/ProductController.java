package com.umar.apps.slowresp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ProductController {
    
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
    @Autowired private ProductRepository productRepository;
    
    @GetMapping("/products/price/{id}")
    public Integer price(@PathVariable("id")Integer productId) {
		long startTime = System.currentTimeMillis();
		logger.info("ProductService-1 - Finding Product with Id:{}", productId);
        Optional<Product> product = productRepository.findById(productId);
        var price = new AtomicInteger(0);
        product.ifPresent(p -> price.set(p.getPrice()));
		long endTime = System.currentTimeMillis();
        System.out.printf("Total time take to execute:{%d}%n", endTime - startTime);
        return price.get();
    }
}


interface ProductRepository extends JpaRepository<Product, Integer> {
    
}

@Entity
class Product {
    @Id @GeneratedValue
    private Integer id;
    private String productName;
    private Integer price;
    
    Product() {}

    public Product(String productName, Integer price) {
        this.productName = productName;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.productName);
        hash = 37 * hash + Objects.hashCode(this.price);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.price, other.price);
    }
    
    
    
}
