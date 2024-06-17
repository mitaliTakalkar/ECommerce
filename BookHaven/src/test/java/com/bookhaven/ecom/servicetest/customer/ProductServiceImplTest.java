package com.bookhaven.ecom.servicetest.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookhaven.ecom.dto.ProductDetailDto;
import com.bookhaven.ecom.dto.ProductDto;
import com.bookhaven.ecom.entity.FAQ;
import com.bookhaven.ecom.entity.Product;
import com.bookhaven.ecom.entity.Review;
import com.bookhaven.ecom.repository.FAQRepository;
import com.bookhaven.ecom.repository.ProductRepository;
import com.bookhaven.ecom.repository.ReviewRepository;
import com.bookhaven.ecom.services.CustomerProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private CustomerProductServiceImpl customerProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FAQRepository faqRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("Test1");

        Product product2 = new Product();
        product2.setName("Test2");

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDto> result = customerProductService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getName());
        assertEquals("Test2", result.get(1).getName());
    }

    @Test
    public void testSearchProductByTitle() {
        Product product1 = new Product();
        product1.setName("Test1");

        List<Product> products = Arrays.asList(product1);

        when(productRepository.findAllByNameContaining("Test1")).thenReturn(products);

        List<ProductDto> result = customerProductService.searchProductByTitle("Test1");

        assertEquals(1, result.size());
        assertEquals("Test1", result.get(0).getName());
    }

    @Test
    public void testGetProductDetailById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");

        FAQ faq = new FAQ();
        faq.setProduct(product);

        Review review = new Review();
        review.setProduct(product);

        List<FAQ> faqs = Arrays.asList(faq);
        List<Review> reviews = Arrays.asList(review);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(faqRepository.findAllByProductId(1L)).thenReturn(faqs);
        when(reviewRepository.findAllByProductId(1L)).thenReturn(reviews);

        ProductDetailDto result = customerProductService.getProductDetailById(1L);

        assertEquals("Test", result.getProductDto().getName());
        assertEquals(1, result.getFaqDtoList().size());
        assertEquals(1, result.getReviewDtoList().size());
    }

    @Test
    public void testGetProductDetailById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductDetailDto result = customerProductService.getProductDetailById(1L);

        assertNull(result);
    }
}
