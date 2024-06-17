package com.bookhaven.ecom.services;

import java.util.List;

import com.bookhaven.ecom.dto.OrderDto;

public interface AdminOrderService {
	List<OrderDto> getAllPlacedOrders();
	OrderDto changeOrderStatus(Long orderId, String status);
}
