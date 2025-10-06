package vn.iotstar.Service;

import java.util.List;

import vn.iotstar.Entity.Product;

public interface ProductService {
    List<Product> listAll();
    Product save(Product product);
    Product get(Long id);
    void delete(Long id);
}
