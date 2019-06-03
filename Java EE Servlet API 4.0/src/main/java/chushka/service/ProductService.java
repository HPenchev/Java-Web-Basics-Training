package chushka.service;

import chushka.models.ProductServiceModels;
import chushka.repository.ProductRepository;

import javax.inject.Inject;

public interface ProductService {
    void saveProduct(ProductServiceModels productServiceModels);
}
