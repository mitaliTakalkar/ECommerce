package com.bookhaven.ecom.services;

import com.bookhaven.ecom.dto.FAQDto;

public interface FAQService {
	FAQDto postFAQ(Long productId, FAQDto faqDto);
}
