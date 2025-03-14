package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("ad0f94c6-9b91-437d-ab8f-d0821dde9096");
        product2.setProductName("Sampo Cap User");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateAndEdit() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(product1.getProductId());
        product2.setProductName("Sampo Cap Bango");
        product2.setProductQuantity(10000);
        Product editedProduct = productRepository.editProduct(product1.getProductId(), product2);

        assertNotNull(editedProduct);
        assertEquals("Sampo Cap Bango", editedProduct.getProductName());
        assertEquals(10000, editedProduct.getProductQuantity());

        Product foundProduct = productRepository.findById(product1.getProductId());
        assertNotNull(foundProduct);
        assertEquals("Sampo Cap Bango", foundProduct.getProductName());
        assertEquals(10000, foundProduct.getProductQuantity());
    }

    @Test
    void testEditProduct_WithNonExistentProduct() {
        Product product = new Product();
        product.setProductId("0");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product editedProduct = productRepository.editProduct(product.getProductId(), product);
        assertNull(editedProduct);
    }

    @Test
    void testCreateAndDelete() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(400);
        productRepository.create(product1);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        boolean deletedProduct = productRepository.deleteProduct(product1.getProductId());

        assertTrue(deletedProduct);
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        boolean deletedProduct = productRepository.deleteProduct("NonExistentProduct");
        assertFalse(deletedProduct);
    }

    @Test
    void testEditAndDelete() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(product1.getProductId());
        product2.setProductName("Sampo Cap Bango");
        product2.setProductQuantity(10000);
        Product editedProduct = productRepository.editProduct(product1.getProductId(), product2);

        assertNotNull(editedProduct);
        productRepository.deleteProduct(editedProduct.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindProductById() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Bango");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product findProduct1 = productRepository.findById(product1.getProductId());
        Product findProduct2 = productRepository.findById(product2.getProductId());

        assertNotNull(findProduct1);
        assertNotNull(findProduct2);
        assertEquals("Sampo Cap Bambang", findProduct1.getProductName());
        assertEquals("Sampo Cap Bango", findProduct2.getProductName());
    }

    @Test
    void testFindNotExistentProductById() {
        Product findProduct1 = productRepository.findById("0");
        assertNull(findProduct1);
    }

    @Test
    void testFindByIdWhenProductDoesNotExist() {
        Product foundProduct = productRepository.findById("0");
        assertNull(foundProduct);
    }

    @Test
    void testEditProductNonExistentWithoutCreatingProduct() {
        Product product = new Product();
        product.setProductId("0");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(111);

        Product editedProduct = productRepository.editProduct("0", product);
        assertNull(editedProduct);
    }

    @Test
    void testDeleteNonExistentProductWithoutCreating() {
        boolean result = productRepository.deleteProduct("0");
        assertFalse(result);
    }

    @Test
    void testEditProductWhenRepositoryIsEmpty() {
        Product editedProduct = new Product();
        editedProduct.setProductId("123");
        editedProduct.setProductName("Sampo Cap Bambang");
        editedProduct.setProductQuantity(50);

        Product result = productRepository.editProduct("123", editedProduct);
        assertNull(result);
    }

    @Test
    void testEditProductWithNullEditedProduct() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product result = productRepository.editProduct("123", null);
        assertNull(result);
    }

    @Test
    void testEditProductWithNullProductId() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product newProduct = new Product();
        newProduct.setProductName("Sampo Cap Baru");
        newProduct.setProductQuantity(200);

        Product result = productRepository.editProduct(null, newProduct);
        assertNull(result);
    }

    @Test
    void testFindByIdWithNullId() {
        Product result = productRepository.findById(null);
        assertNull(result);
    }

    @Test
    void testDeleteProductWithNullId() {
        boolean result = productRepository.deleteProduct(null);
        assertFalse(result);
    }

    @Test
    void testEditProductWithNullEditedProductWhenProductExists() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product result = productRepository.editProduct("existing-id", null);

        assertNull(result);
    }

    @Test
    void testEditProductWhenMultipleProductsExistButIdDoesNotMatch() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Sampo Cap A");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Sampo Cap B");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Product editedProduct = new Product();
        editedProduct.setProductId("non-existent-id");
        editedProduct.setProductName("Sampo Cap X");
        editedProduct.setProductQuantity(500);

        Product result = productRepository.editProduct("non-existent-id", editedProduct);
        assertNull(result);
    }
}
