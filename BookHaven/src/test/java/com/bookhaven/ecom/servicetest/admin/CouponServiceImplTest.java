package com.bookhaven.ecom.servicetest.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookhaven.ecom.entity.Coupon;
import com.bookhaven.ecom.exceptions.ValidationException;
import com.bookhaven.ecom.repository.CouponRepository;
import com.bookhaven.ecom.services.AdminCouponServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest {

    @InjectMocks
    private AdminCouponServiceImpl adminCouponService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    public void testCreateCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCode("Test");

        when(couponRepository.existsByCode(coupon.getCode())).thenReturn(false);
        when(couponRepository.save(coupon)).thenReturn(coupon);

        Coupon createdCoupon = adminCouponService.createCoupon(coupon);

        assertEquals(coupon.getCode(), createdCoupon.getCode());
    }

    @Test
    public void testCreateCoupon_ThrowsException() {
        Coupon coupon = new Coupon();
        coupon.setCode("Test");

        when(couponRepository.existsByCode(coupon.getCode())).thenReturn(true);

        assertThrows(ValidationException.class, () -> adminCouponService.createCoupon(coupon));
    }

    @Test
    public void testGetAllCoupons() {
        Coupon coupon1 = new Coupon();
        coupon1.setCode("Test1");

        Coupon coupon2 = new Coupon();
        coupon2.setCode("Test2");

        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponRepository.findAll()).thenReturn(coupons);

        List<Coupon> result = adminCouponService.getAllCoupons();

        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getCode());
        assertEquals("Test2", result.get(1).getCode());
    }
}
