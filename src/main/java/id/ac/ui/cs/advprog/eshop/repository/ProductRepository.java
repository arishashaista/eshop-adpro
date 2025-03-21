package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public Product editProduct(String productId, Product editedProduct) {
        if (editedProduct == null || productId == null) {
            return null;
        }

        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(productId)) {
                editedProduct.setProductId(productId);
                productData.set(i, editedProduct);
                return editedProduct;
            }
        }
        return null;
    }

    public boolean deleteProduct(String productId) {
        return productData.removeIf(product -> product.getProductId().equals(productId));
    }
}
