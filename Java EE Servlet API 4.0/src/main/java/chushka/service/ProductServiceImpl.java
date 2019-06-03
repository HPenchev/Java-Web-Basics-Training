package chushka.service;

import chushka.models.ProductServiceModels;
import chushka.repository.ProductRepository;

import javax.inject.Inject;

public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductRepository productRepository;

    @Override
    public void saveProduct(ProductServiceModels productServiceModels) {

    }
}
