package vn.iuh.iuh.fit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iuh.iuh.fit.models.Product;
import vn.iuh.iuh.fit.repositories.ProductRepository;
import vn.iuh.iuh.fit.services.ProductServices;

@Service
public class ProductServicesImpl implements ProductServices {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
