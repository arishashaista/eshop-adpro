package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        verify(productRepository, times(1)).create(product);
        assertEquals(product, result);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Product product2 = new Product();
        product2.setProductId("abc-123");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(product, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    void testFindAllEmpty() {
        List<Product> emptyList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(emptyList.iterator());

        List<Product> result = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product result = productService.findById(product.getProductId());

        verify(productRepository, times(1)).findById(product.getProductId());
        assertEquals(product, result);
    }

    @Test
    void testFindByIdNotFound() {
        String nonExistentId = "non-existent-id";
        when(productRepository.findById(nonExistentId)).thenReturn(null);

        Product result = productService.findById(nonExistentId);

        verify(productRepository, times(1)).findById(nonExistentId);
        assertNull(result);
    }

    @Test
    void testEditProduct() {
        Product editedProduct = new Product();
        editedProduct.setProductId(product.getProductId());
        editedProduct.setProductName("Sampo Cap Bango");
        editedProduct.setProductQuantity(200);

        when(productRepository.editProduct(product.getProductId(), editedProduct))
                .thenReturn(editedProduct);

        Product result = productService.editProduct(product.getProductId(), editedProduct);

        verify(productRepository, times(1))
                .editProduct(product.getProductId(), editedProduct);
        assertEquals(editedProduct, result);
    }

    @Test
    void testEditProductNotFound() {
        String nonExistentId = "non-existent-id";
        when(productRepository.editProduct(nonExistentId, product)).thenReturn(null);

        Product result = productService.editProduct(nonExistentId, product);

        verify(productRepository, times(1)).editProduct(nonExistentId, product);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.deleteProduct(product.getProductId())).thenReturn(true);

        boolean result = productService.deleteProduct(product.getProductId());

        verify(productRepository, times(1)).deleteProduct(product.getProductId());
        assertTrue(result);
    }

    @Test
    void testDeleteProductNotFound() {
        String nonExistentId = "non-existent-id";
        when(productRepository.deleteProduct(nonExistentId)).thenReturn(false);

        boolean result = productService.deleteProduct(nonExistentId);

        verify(productRepository, times(1)).deleteProduct(nonExistentId);
        assertFalse(result);
    }
}