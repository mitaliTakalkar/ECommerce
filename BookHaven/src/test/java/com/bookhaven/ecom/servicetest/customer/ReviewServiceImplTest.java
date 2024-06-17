package com.bookhaven.ecom.servicetest.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookhaven.ecom.dto.OrderedProductsResponseDto;
import com.bookhaven.ecom.dto.ReviewDto;
import com.bookhaven.ecom.entity.CartItems;
import com.bookhaven.ecom.entity.Order;
import com.bookhaven.ecom.entity.Product;
import com.bookhaven.ecom.repository.OrderRepository;
import com.bookhaven.ecom.repository.ProductRepository;
import com.bookhaven.ecom.repository.ReviewRepository;
import com.bookhaven.ecom.repository.UserRepository;
import com.bookhaven.ecom.services.ReviewServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void testGetOrderedProductsDetailsByOrderId() {
        Order order = new Order();
        order.setAmount(100L);

        CartItems cartItems = new CartItems();
        cartItems.setProduct(new Product());
        cartItems.setPrice(100L);
        cartItems.setQuantity(1L);
        cartItems.getProduct().setId(1L);
        cartItems.getProduct().setName("Test");
        cartItems.getProduct().setImg("Test Image".getBytes());

        order.setCartItems(Arrays.asList(cartItems));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderedProductsResponseDto result = reviewService.getOrderedProductsDetailsByOrderId(1L);

        assertEquals(100, result.getOrderAmount());
        assertEquals(1, result.getProductDtoList().size());
        assertEquals("Test", result.getProductDtoList().get(0).getName());
    }

    @Test
    public void testGiveReview_ProductNotFound() throws IOException {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(1L);
        reviewDto.setUserId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ReviewDto createdReview = reviewService.giveReview(reviewDto);

        assertNull(createdReview);
    }

    @Test
    public void testGiveReview_UserNotFound() throws IOException {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(1L);
        reviewDto.setUserId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ReviewDto createdReview = reviewService.giveReview(reviewDto);

        assertNull(createdReview);
    }
}
