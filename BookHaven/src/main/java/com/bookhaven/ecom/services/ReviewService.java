package com.bookhaven.ecom.services;
 
import java.io.IOException;

import com.bookhaven.ecom.dto.OrderedProductsResponseDto;
import com.bookhaven.ecom.dto.ReviewDto;
 
public interface ReviewService {
 
	OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
	ReviewDto giveReview(ReviewDto reviewDto) throws IOException ;
}