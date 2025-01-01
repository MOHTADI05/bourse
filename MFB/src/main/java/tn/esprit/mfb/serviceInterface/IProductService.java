package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.User;
import tn.esprit.mfb.entity.Product;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void saveProduct(Product product);
    void deleteProduct(Long productId);
    Product saveOrUpdateProduct(Product product);
    Product updateProduct(Product prod, Long partnerid);
    boolean acheter(User client, Product product, int periode);
}
