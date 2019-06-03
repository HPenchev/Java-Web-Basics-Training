package chushka.service;

import chushka.domain.entities.Product;
import chushka.domain.entities.Type;
import chushka.models.ProductServiceModels;
import chushka.repository.ProductRepository;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import java.awt.*;

public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductRepository productRepository;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public void saveProduct(ProductServiceModels productServiceModels) {
        Product product = modelMapper.map(productServiceModels, Product.class);
        product.setType(Type.valueOf(productServiceModels.getType()));
        productRepository.save(product);

    }
}
