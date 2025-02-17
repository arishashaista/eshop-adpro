package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller;

    private Product product;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productList = new ArrayList<>();
        productList.add(product);
    }

    @Test
    void testCreateProductPage() {
        String viewName = controller.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        when(service.create(product)).thenReturn(product);

        String viewName = controller.createProductPost(product, model);

        verify(service).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        when(service.findAll()).thenReturn(productList);

        String viewName = controller.productListPage(model);

        verify(service).findAll();
        verify(model).addAttribute("products", productList);
        assertEquals("ProductList", viewName);
    }

    @Test
    void testEditProductPage_ProductFound() {
        when(service.findById(product.getProductId())).thenReturn(product);

        String viewName = controller.editProductPage(product.getProductId(), model);

        verify(service).findById(product.getProductId());
        verify(model).addAttribute("product", product);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        String nonExistentId = "non-existent-id";
        when(service.findById(nonExistentId)).thenReturn(null);

        String viewName = controller.editProductPage(nonExistentId, model);

        verify(service).findById(nonExistentId);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPost() {
        when(service.editProduct(product.getProductId(), product)).thenReturn(product);

        String viewName = controller.editProductPost(product.getProductId(), product);

        verify(service).editProduct(product.getProductId(), product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct() {
        when(service.deleteProduct(product.getProductId())).thenReturn(true);

        String viewName = controller.deleteProduct(product.getProductId());

        verify(service).deleteProduct(product.getProductId());
        assertEquals("redirect:/product/list", viewName);
    }
}